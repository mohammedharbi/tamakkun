package com.example.tamakkun.Service;

import com.example.tamakkun.API.ApiException;
import com.example.tamakkun.DTO_In.ParentDTO_In;
import com.example.tamakkun.DTO_Out.BookingDTO_Out;
import com.example.tamakkun.DTO_Out.CentreDTO_Out;
import com.example.tamakkun.DTO_Out.ParentDTO_Out;
import com.example.tamakkun.DTO_Out.ReviewDTO_Out;
import com.example.tamakkun.Model.*;
import com.example.tamakkun.Repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class ParentService {
    private final ParentRepository parentRepository;
    private final AuthRepository authRepository;
    private final BookingRepository bookingRepository;
    private final ReviewRepository reviewRepository;
    private final CentreRepository centreRepository;

    //#
    public ParentDTO_Out getParentById (Integer user_id ){
        MyUser user = authRepository.findMyUserById(user_id);
        if (user==null){
            throw new ApiException(" user not found");
        }
        Parent parent= parentRepository.findParentByMyUser(user);
        return new ParentDTO_Out(parent.getFullName(), parent.getPhoneNumber(), parent.getAddress());
    }


    public void register (ParentDTO_In parentDTOIn){
        MyUser myUser =new MyUser();
        myUser.setUsername(parentDTOIn.getUsername());
        String hashPassword= new BCryptPasswordEncoder().encode(parentDTOIn.getPassword());
        myUser.setPassword(hashPassword);
        myUser.setRole("PARENT");
        myUser.setEmail(parentDTOIn.getEmail());
        authRepository.save(myUser);
        Parent parent=new Parent();
        parent.setFullName(parentDTOIn.getFullName());
        parent.setPhoneNumber(parentDTOIn.getPhoneNumber());
        parent.setAddress(parentDTOIn.getAddress());
        parent.setMyUser(myUser);
        parentRepository.save(parent);
    }

    public void update (Integer user_id , ParentDTO_In parentDTOIn){
        MyUser myUser= authRepository.findMyUserById(user_id);
        if (myUser==null){
            throw new ApiException("wrong user id");
        }

        Parent parent= parentRepository.findParentByMyUser(myUser);
        myUser.setUsername(parentDTOIn.getUsername());
        String hashPassword= new BCryptPasswordEncoder().encode(parentDTOIn.getPassword());
        myUser.setPassword(hashPassword);
        myUser.setEmail(parentDTOIn.getEmail());
        authRepository.save(myUser);
        parent.setFullName(parentDTOIn.getFullName());
        parent.setPhoneNumber(parentDTOIn.getPhoneNumber());
        parent.setAddress(parentDTOIn.getAddress());
        parentRepository.save(parent);
    }


    //Admin
    public void delete (Integer user_id){
        MyUser myUser = authRepository.findMyUserById(user_id);
        if (myUser==null){
            throw new ApiException("user not found");
        }
        Parent parent= myUser.getParent();
        parentRepository.delete(parent);
        myUser.setParent(null);
        authRepository.delete(myUser);
    }

    //E:#6 Mohammed
    public List<BookingDTO_Out> getAllOldBookingsByParent(Integer parent_id){

        List<BookingDTO_Out> oldBookings = new ArrayList<>();

        // check if parent exist
        Parent parent = parentRepository.findParentById(parent_id);
        if (parent == null) {throw new ApiException("Parent not found!");}

        // get all bookings with the same parent
        List<Booking> bookings = bookingRepository.findBookingsByParent(parent);
        if (bookings == null) {throw new ApiException("Not found any bookings for the parent!");}

        // get only old bookings
        LocalDateTime currentDateTime = LocalDateTime.now();
        for (Booking booking : bookings) {
            if (booking.getBookingDate().getStartTime().isBefore(currentDateTime)) {
                //change it to DTO OUT
                BookingDTO_Out bookingDTO = new BookingDTO_Out(booking.getParent().getFullName(),
                        booking.getChild().getFullName(),booking.getBookingDate().getSpecialist().getName(),
                        booking.getStartTime().toLocalDate(),booking.getStartTime().toLocalTime(),booking.getHours(),
                        booking.getTotalPrice());
                oldBookings.add(bookingDTO);
            }
        }
        if (oldBookings.isEmpty()){throw new ApiException("Not found any old bookings for the parent!");}

        return oldBookings;
    }

    //E:#7 Mohammed
    public List<BookingDTO_Out> getAllNewBookingsByParent(Integer parent_id){

        List<BookingDTO_Out> newBookings = new ArrayList<>();

        // check if parent exist
        Parent parent = parentRepository.findParentById(parent_id);
        if (parent == null) {throw new ApiException("Parent not found!");}

        // get all bookings with the same center
        List<Booking> bookings = bookingRepository.findBookingsByParent(parent);
        if (bookings == null) {throw new ApiException("Not found any bookings for the parent!");}

        // get only new bookings
        LocalDateTime currentDateTime = LocalDateTime.now();
        for (Booking booking : bookings) {
            if (booking.getBookingDate().getStartTime().isBefore(currentDateTime)) {
                //change it to DTO OUT
                BookingDTO_Out bookingDTO = new BookingDTO_Out(booking.getParent().getFullName(),
                        booking.getChild().getFullName(),booking.getBookingDate().getSpecialist().getName(),
                        booking.getStartTime().toLocalDate(),booking.getStartTime().toLocalTime(),booking.getHours(),
                        booking.getTotalPrice());
                newBookings.add(bookingDTO);
            }
        }
        if (newBookings.isEmpty()){throw new ApiException("Not found any new bookings for the parent!");}

        return newBookings;
    }

    //E:#8 Mohammed
    public List<BookingDTO_Out> getAllUnReviewedBookingsByParent(Integer parent_id){

        List<BookingDTO_Out> Bookings = new ArrayList<>();

        // check if parent exist
        Parent parent = parentRepository.findParentById(parent_id);
        if (parent == null) {throw new ApiException("Parent not found!");}

        // get all bookings with the same center
        List<Booking> bookings = bookingRepository.findBookingsByParent(parent);
        if (bookings == null) {throw new ApiException("Not found any bookings for the parent!");}

        // get only new bookings
        LocalDateTime currentDateTime = LocalDateTime.now();
        for (Booking booking : bookings) {
            if (booking.getStatus().equalsIgnoreCase("Completed")) {
                //change it to DTO OUT
                BookingDTO_Out bookingDTO = new BookingDTO_Out(booking.getParent().getFullName(),
                        booking.getChild().getFullName(),booking.getBookingDate().getSpecialist().getName(),
                        booking.getStartTime().toLocalDate(),booking.getStartTime().toLocalTime(),booking.getHours(),
                        booking.getTotalPrice());
                Bookings.add(bookingDTO);
            }
        }
        if (Bookings.isEmpty()){throw new ApiException("Not found any unreviewed bookings for the parent!");}

        return Bookings;
    }


    //E:#9 Mohammed
    public List<ReviewDTO_Out> getAllReviewedBookingsByParent(Integer parent_id){
        List<ReviewDTO_Out> reviewDTOOuts = new ArrayList<>();

        Parent parent = parentRepository.findParentById(parent_id);
        if (parent == null) {throw new ApiException("Parent not found!");}

        List<Review> reviews = reviewRepository.findReviewByParent(parent);

        for (Review review : reviews) {
            ReviewDTO_Out reviewDTOOut = new ReviewDTO_Out(review.getRatingCentre(), review.getRatingSpecialist(),
                    review.getComment(), review.getParent().getFullName(), review.getCentre().getName(),
                    review.getSpecialist().getName());
            reviewDTOOuts.add(reviewDTOOut);
        }
        if (reviews.isEmpty()){throw new ApiException("Not found any reviews for the parent!");}
        return reviewDTOOuts;
    }

    //E:#13 Mohammed
    public List<CentreDTO_Out> recommendationToParentsByCentresAndActivities(Integer parentId) {
        List<CentreDTO_Out> recommendations = new ArrayList<>();

        // check the parent
        Parent parent = parentRepository.findParentById(parentId);
        if (parent == null) {
            throw new ApiException("Parent not found!");
        }

        // Gather all disabilities of the parent's children into a Set
        Set<String> childDisabilities = new HashSet<>();
        for (Child child : parent.getChildren()) {
            childDisabilities.add(child.getDisabilityType());
        }

        for (Centre centre : centreRepository.findAll()) {
            boolean isRecommended = false;

            for (Activity activity : centre.getActivitySet()) {
                for (String allowedDisability : activity.getAllowedDisabilities()) {
                    if (childDisabilities.contains(allowedDisability)) {
                        isRecommended = true;
                        break;
                    }
                }
                if (isRecommended) {break;}
            }

            // If the centre is recommended, add it to the list
            if (isRecommended) {
                CentreDTO_Out centreDTOOut = new CentreDTO_Out(
                        centre.getName(),
                        centre.getDescription(),
                        centre.getAddress(),
                        centre.getOpeningHour(),
                        centre.getClosingHour(),
                        centre.getPricePerHour(),
                        centre.getImageUrl()
                );
                recommendations.add(centreDTOOut);
            }
        }

        //   if no recommendations are found
        if (recommendations.isEmpty()) {throw new ApiException("No suitable recommendations found for the parent!");}

        return recommendations;
    }

    //E:#15 Mohammed
    public void cancelBooking(Integer parent_id, Integer booking_id) {
        // check if parent exists
        Parent parent = parentRepository.findParentById(parent_id);
        if (parent == null) {
            throw new ApiException("Parent not found!");
        }

        // check if booking exists
        Booking booking = bookingRepository.findBookingById(booking_id);
        if (booking == null) {
            throw new ApiException("Booking not found!");
        }

        if (!booking.getParent().getId().equals(parent.getId())) {throw new ApiException("parent dosen't own this booking");}

        if (booking.getStatus().equals("Cancelled")){throw new ApiException("Booking is already cancelled");}

        // booking can only be canceled if it's less than 24 hours
        LocalDateTime now = LocalDateTime.now();
        if (booking.getStartTime().isBefore(now.plusHours(24))) {
            throw new ApiException("Booking can only be canceled less than 24 hours before the start time!");
        }

        booking.setStatus("Cancelled");
        bookingRepository.save(booking);
    }
}
