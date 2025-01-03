package com.example.tamakkun.Service;

import com.example.tamakkun.API.ApiException;
import com.example.tamakkun.Model.*;
import com.example.tamakkun.Repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

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
    public void newBooking(Integer parent_id, Integer child_id, Integer centre_id, Integer hours, Booking booking) {

        // Validate Parent
        Parent parent = parentRepository.findParentById(parent_id);
        if(parent == null) throw new ApiException("Parent not found.");

        // Validate Child and Parent-Child Relationship
        Child child = childRepository.findChildById(child_id);
        if(child == null) throw new ApiException("Child not found.");
        if(!child.getParent().getId().equals(parent_id)) throw new ApiException("The parent does not match the child.");

        // Validate Centre
        Centre centre = centreRepository.findCentreById(centre_id);
        if(centre == null) throw new ApiException("Centre not found.");
        //if(!centre.getIsVerified())throw new ApiException("The centre is not verified.");

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
                        booking.getStartTime().plusHours(hours), booking, centre, specialist);
                Booking newBooking = new Booking(null, booking.getStartTime(), hours, "Pending",
                        centre.getPricePerHour() * hours,booking.getNotifyMe(),false, newBookingDate, parent, child, centre);

                bookingRepository.save(newBooking);
                bookingDateRepository.save(newBookingDate);
                ///////////////////////////////////
                // durrah
                // email
                // QRCode
                ///////////////////////////////////
                return; // Exit after successful booking
            }
        }

        throw new ApiException("No available specialists for the specified time.");
    }

    public Boolean checkIfSupportedDisabilities(Specialist specialist, Child child) {
        return specialist.getSupportedDisabilities().contains(child.getDisabilityType());
    }
}