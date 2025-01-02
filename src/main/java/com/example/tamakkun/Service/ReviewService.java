package com.example.tamakkun.Service;

import com.example.tamakkun.API.ApiException;
import com.example.tamakkun.DTO_Out.ReviewDTO_Out;
import com.example.tamakkun.Model.*;
import com.example.tamakkun.Repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final AuthRepository authRepository;
    private final ParentRepository parentRepository;
    private final ChildRepository childRepository;
    private final CentreRepository centreRepository;
    private final SpecialistRepository specialistRepository;


    public List<ReviewDTO_Out> getAllReviews(){
        List<Review> reviews = reviewRepository.findAll();
        List<ReviewDTO_Out> reviewDTOOUTS = new ArrayList<>();

        for (Review r : reviews){
            ReviewDTO_Out reviewDTOOUT = new ReviewDTO_Out(r.getRatingCentre(),r.getRatingSpecialist(),r.getComment(),r.getParent().getFullName(),r.getCentre().getName(),r.getSpecialist().getName());
            reviewDTOOUTS.add(reviewDTOOUT);
        }
        return reviewDTOOUTS;
    }


    public void addReviewCentre(Integer parent_id, Integer centre_id, Integer specialist_id, Review review) {
        Parent parent = parentRepository.findParentById(parent_id);

        Centre centre = centreRepository.findCentreById(centre_id);
        Specialist specialist = specialistRepository.findSpecialistById(specialist_id);


        // Validate user and service provider existence
        if (parent == null || centre == null || specialist == null) {
            throw new ApiException("Cannot review: parent or centre or specialist not found.");
        }

        // Retrieve only relevant centres
        List<Booking> centres = reviewRepository.findCompletedOffersByMyUserAndCentre(parent_id, centre_id);

        // Ensure the user has completed an offer with this centre
        if (centres.isEmpty()) {
            throw new ApiException("Parent does not have completed bookings with this service provider.");
        }



        // Add review to centre
        parent.getReviews().add(review);
        review.setCentre(centre);
        review.setParent(parent);
        review.setSpecialist(specialist);

        // Update the average rating dynamically
//        int totalReviews = centre.getReviews().size();
//        double newAverage = ((centre.getAverageRating() * (totalReviews - 1)) + review.getRating()) / totalReviews;
//        centre.setAverageRating(newAverage);

        // Save the review
        reviewRepository.save(review);
        centreRepository.save(centre);
        parentRepository.save(parent);
    }
}
