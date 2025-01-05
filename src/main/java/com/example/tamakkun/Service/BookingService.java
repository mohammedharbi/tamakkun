package com.example.tamakkun.Service;

import com.example.tamakkun.API.ApiException;
import com.example.tamakkun.Model.*;
import com.example.tamakkun.Repository.*;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.qrcode.QRCodeWriter;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BookingService {

    private final BookingRepository bookingRepository;
    private final BookingDateRepository bookingDateRepository;
    private final ParentRepository parentRepository;
    private final ChildRepository childRepository;
    private final CentreRepository centreRepository;
    private final SpecialistRepository specialistRepository;
    private final EmailService emailService;



    public void newBooking(Integer parent_id, Integer child_id, Integer centre_id, Integer hours, Booking booking) {

        // Validate Parent
        Parent parent = parentRepository.findParentById(parent_id);
        if(parent == null) throw new ApiException("Parent not found.");

        if (!parent.getIsActive()){throw new ApiException("Parent is not active therefore is not permitted to access this service!");}

        // Validate Child and Parent-Child Relationship
        Child child = childRepository.findChildById(child_id);
        if(child == null) throw new ApiException("Child not found.");
        if(!child.getParent().getId().equals(parent_id)) throw new ApiException("The parent does not match the child.");

        // Validate Centre
        Centre centre = centreRepository.findCentreById(centre_id);
        if(centre == null) throw new ApiException("Centre not found.");

        if(!centre.getIsVerified())throw new ApiException("The centre is not verified.");

        // Validate Booking Time
        LocalTime bookingStartTime = booking.getStartTime().toLocalTime();
        boolean isWithinOperatingHours = (centre.getClosingHour().isBefore(centre.getOpeningHour())) ?
                (bookingStartTime.isAfter(centre.getOpeningHour()) || bookingStartTime.isBefore(centre.getClosingHour())) :
                (bookingStartTime.isAfter(centre.getOpeningHour()) && bookingStartTime.isBefore(centre.getClosingHour()));

        if (!isWithinOperatingHours) throw new ApiException("Booking is outside operating hours.");

        // Filter Specialists with Matching Disabilities
        List<Specialist> matchingSpecialists = centre.getSpecialists().stream()
                .filter(s -> checkIfSupportedDisabilities(s, child))
                .toList();

        if (matchingSpecialists.isEmpty()) {
            throw new ApiException("No specialist supports the child's disabilities.");
        }

        // Check Specialist Availability and Create Booking
        for (Specialist specialist : matchingSpecialists) {
            boolean isAvailable = bookingDateRepository.findAllBySpecialist(specialist).stream()
                    .noneMatch(existingBooking ->
                            !(existingBooking.getEndTime().isBefore(booking.getStartTime()) ||
                                    existingBooking.getStartTime().isAfter(booking.getStartTime().plusHours(hours))));

            if (isAvailable) {
                BookingDate newBookingDate = new BookingDate(null, booking.getStartTime(),
                        booking.getStartTime().plusHours(booking.getHours()), booking, centre, specialist);

                newBookingDate.getBooking().setChild(child);
                newBookingDate.getBooking().setCentre(centre);
                newBookingDate.getBooking().setParent(parent);
                newBookingDate.getBooking().setTotalPrice(centre.getPricePerHour() * booking.getHours());
                newBookingDate.getBooking().setStatus("Pending");

                bookingDateRepository.save(newBookingDate);
                 // Generate QR Code and send email
                try {
                    if (booking.getIsScanned()) {
                        throw new ApiException("Cannot generate a QR code for a scanned booking!");
                    }

                    byte[] qrCode = generateQRCode(booking); // Generate QR code method calling
                    // Send email notification
                    sendBookingNotification(booking.getParent().getMyUser().getEmail(), booking, qrCode);

                } catch (Exception e) {
                    throw new ApiException("Failed to generate QR code or send email! " + e.getMessage());
                }


                return; // Exit after successful booking
            }
        }

        throw new ApiException("No available specialists for the specified time!");
    }//end

    public Boolean checkIfSupportedDisabilities(Specialist specialist, Child child) {
        return specialist.getSupportedDisabilities().contains(child.getDisabilityType());
    }


    // Helper method to generate QR code as PNG image with email attachment
    private byte[] generateQRCode(Booking booking) throws WriterException, IOException {
        QRCodeWriter qrCodeWriter = new QRCodeWriter();

        // QR code content includes essential booking details
        String qrContent = String.format("Booking ID: %d\nCentre: %s\nSpecialist: %s (%s)\nStart: %s\nEnd: %s\nTotal Price: %.2f",
                booking.getId(),
                booking.getCentre().getName(),
                booking.getBookingDate().getSpecialist().getName(),
                booking.getBookingDate().getSpecialist().getPhoneNumber(),
                booking.getStartTime().toString(),
                booking.getStartTime().plusHours(booking.getHours()).toString(),
                booking.getTotalPrice());

        var bitMatrix = qrCodeWriter.encode(qrContent, BarcodeFormat.QR_CODE, 300, 300);

        try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {
            MatrixToImageWriter.writeToStream(bitMatrix, "PNG", outputStream);
            return outputStream.toByteArray();
        }

    }

    // Helper method to send an email with booking details to parent's email

    private void sendBookingNotification(String email, Booking booking, byte[] qrCode) {
        String subject = "Your Booking Confirmation!";

        String body = String.format("Dear %s,\n\nYour booking has been confirmed.\n\nDetails:\nCentre: %s\nSpecialist: %s\nStart: %s\nEnd: %s\nTotal Price: %.2f\n\nPlease find your QR code attached.",
                booking.getParent().getFullName(),
                booking.getCentre().getName(),
                booking.getBookingDate().getSpecialist().getName(),
                booking.getStartTime().toString(),
                booking.getStartTime().plusHours(booking.getHours()),
                booking.getTotalPrice());

        emailService.sendEmailWithAttachment(email, subject, body, qrCode, "BookingQRCode.png");
    }

    public void markBookingAsScanned(Integer bookingId) {

        Booking booking = bookingRepository.findById(bookingId)
                .orElseThrow(() -> new ApiException("Booking not found!"));

        // Check if the booking is already scanned
        if (Boolean.TRUE.equals(booking.getIsScanned())) {
            throw new ApiException("This booking has already been scanned and completed!");
        }

        // mark the booking as scanned and its ststus completed
        booking.setIsScanned(true);
        booking.setStatus("Completed");

        bookingRepository.save(booking);

    }


    /**
     * Sends reminders to parents with bookings 1 day from now.
     */
   // @Scheduled(cron = "0 0 10 * * ?") // Scheduled to run daily at 10:00AM
    @Scheduled(cron = "*/10 * * * * ?") // Scheduled to run every 10 seconds
    public void sendBookingReminders() {
        LocalDateTime now = LocalDateTime.now();

        // all bookings
        List<Booking> bookings = bookingRepository.findAll().stream()
                .filter(booking -> Boolean.TRUE.equals(booking.getNotifyMe()) &&
                        Boolean.FALSE.equals(booking.getIsAlerted()) &&
                        booking.getStartTime().toLocalDate().isEqual(now.plusDays(1).toLocalDate()))
                .toList();

        if (bookings.isEmpty()) {
            System.out.println("No reminders to send.");
            return;
        }

        for (Booking booking : bookings) {
            try {
                // email's details
                if (booking.getParent() != null && booking.getParent().getMyUser() != null) {

                    String parentEmail = booking.getParent().getMyUser().getEmail();
                    String subject = "\uD83C\uDF1F Reminder: Your Booking Tomorrow \n" + "\uD83C\uDF1F";
                    String body = String.format(
                            "Dear %s,\n\nThis is a friendly reminder about your child's booking tomorrow at %s.\n\nBooking Details:\nCentre: %s\nStart Time: %s\nTotal Price: %.2f\n\nThank you :)",
                            booking.getParent().getFullName(),
                            booking.getCentre().getName(),
                            booking.getCentre().getName(),
                            booking.getStartTime().toString(),
                            booking.getTotalPrice()
                    );

                    emailService.sendEmail(parentEmail, subject, body);

                    // mark booking as alerted
                    booking.setIsAlerted(true);
                    bookingRepository.save(booking);
                }
            } catch (Exception e) {
                System.out.println("Failed to send reminder for booking ID: " + booking.getId() + ". Error: " + e.getMessage());
            }
        }
    }

    //E:#12 Mohammed
    @Scheduled(cron = "0 0 * * * ?") // the task will execute at the beginning of every hour (e.g., 1:00 PM, 2:00 PM, etc.).
    public void updateBookingStatus() {
        for (Booking booking : bookingRepository.findAll()) {
            if (booking.getStatus().equalsIgnoreCase("Pending")) {
                if (booking.getBookingDate().getEndTime().isBefore(LocalDateTime.now())) {
                    booking.setStatus("Completed");
                    booking.setIsAlerted(true);
                    bookingRepository.save(booking);
                }
            }
        }
    }

    //E:#13 Mohammed
    @Scheduled(cron = "0 0 * * * ?") // the task will execute at the beginning of every hour (e.g., 1:00 PM, 2:00 PM, etc.).
    public void autoRequestForReviewAfterVisit(){

        for (Booking booking : bookingRepository.findAll()) {
            if (booking.getStatus().equalsIgnoreCase("Completed")) {
                if (!booking.getIsReviewed()&& !booking.getIsAskedToReview()){
                    //  email's details
                    String parentEmail = booking.getParent().getMyUser().getEmail();
                    String subject = "Thank You for Your Visiting - We Value Your Feedback!";
                    String body = String.format(
                            "Dear %s,\n\n"
                                    + "We hope this message finds you well. Thank you for completing your recent booking with us. "
                                    + "We are committed to providing the best experience for you and your family.\n\n"
                                    + "Booking Details:\n"
                                    + "Centre: %s\n"
                                    + "Start Time: %s\n"
                                    + "Total Price: %.2f\n\n"
                                    + "We would greatly appreciate it if you could take a moment to share your feedback. Your thoughts help us improve and ensure we continue delivering exceptional service.\n\n"
                                    + "Please click the link below to leave your review:\n"
                                    + "https://www.tamakkun.com/review\n\n"
                                    + "Thank you for choosing us. We look forward to serving you again soon.\n\n"
                                    + "Best regards,\n"
                                    + "The Tamakkun Team",
                            booking.getParent().getFullName(),
                            booking.getCentre().getName(),
                            booking.getStartTime().toString(),
                            booking.getTotalPrice()
                    );

                    emailService.sendEmail(parentEmail, subject, body);
                    booking.setIsAskedToReview(true);
                    bookingRepository.save(booking);

                }
            }
        }
    }



}