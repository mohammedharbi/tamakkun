package com.example.tamakkun.Service;

import com.example.tamakkun.API.ApiException;
import com.example.tamakkun.DTO_Out.ReviewDTO_Out;
import com.example.tamakkun.DTO_Out.SpecialistRatingDTO;
import com.example.tamakkun.Model.*;
import com.example.tamakkun.Repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final AuthRepository authRepository;
    private final ParentRepository parentRepository;
    private final ChildRepository childRepository;
    private final CentreRepository centreRepository;
    private final SpecialistRepository specialistRepository;
    private final BookingRepository bookingRepository;


    public List<Review> getAllReviews(){
        return reviewRepository.findAll();
    }


    //E:#2 Mohammed
    public void newReview(Integer parent_id, Integer booking_id, Review review) {
        Parent parent = parentRepository.findParentById(parent_id);

        //booking checks
        Booking booking = bookingRepository.findBookingById(booking_id);
        if (booking == null) {throw new ApiException("booking not found");}
        if (!booking.getParent().getId().equals(parent.getId())) {throw new ApiException("parent isn't the owner of this booking");}
        if (!booking.getStatus().equalsIgnoreCase("Completed")){throw new ApiException("booking isn't completed, booking must be Completed in order to be reviewed");}
        if (booking.getIsReviewed()){throw new ApiException("booking is already reviewed");}

        Centre centre = centreRepository.findCentreById(booking.getCentre().getId());
        Specialist specialist = specialistRepository.findSpecialistById(booking.getBookingDate().getSpecialist().getId());


        // Validate parent and centre existence
        if (parent == null || centre == null || specialist == null) {
            throw new ApiException("Cannot review: parent or centre or specialist not found.");
        }

        // Retrieve only relevant centres
        List<Booking> centres = reviewRepository.findCompletedBookingsByMyUserAndCentre(parent_id, booking.getCentre().getId());

        // Ensure the user has completed an offer with this centre
        if (centres.isEmpty()) {
            throw new ApiException("Parent does not have completed bookings with this centre.");
        }

        // Add review to centre
        parent.getReviews().add(review);
        review.setCentre(centre);
        review.setParent(parent);
        review.setSpecialist(specialist);
        booking.setIsReviewed(true);

        // Save the review
        reviewRepository.save(review);
        centreRepository.save(centre);
        parentRepository.save(parent);
        bookingRepository.save(booking);
    }


    public List<ReviewDTO_Out> getReviewsByCentre(Integer centreId) {
        Centre centre = centreRepository.findCentreById(centreId);
        if (centre == null){throw new ApiException("centre not found!");}

        List<Review> reviews = reviewRepository.findReviewByCentre(centre);

        return reviews.stream()
                .map(review -> new ReviewDTO_Out(
                        review.getRatingCentre(),
                        review.getRatingSpecialist(),
                        review.getComment(),
                        review.getParent() != null ? review.getParent().getFullName() : null,  // Parent name
                        review.getCentre() != null ? review.getCentre().getName() : null,     // Centre name
                        review.getSpecialist() != null ? review.getSpecialist().getName() : null  // Specialist name
                ))
                .collect(Collectors.toList());
    }


    public List<Specialist> getTopThreeRatedSpecialistsByCentre(Integer centreId) {
        List<Specialist> specialists = reviewRepository.findTopThreeRatedSpecialistsByCentre(centreId);
        return specialists.stream().limit(3).toList();
    }

}
