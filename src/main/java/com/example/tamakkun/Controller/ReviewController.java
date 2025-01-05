package com.example.tamakkun.Controller;

import com.example.tamakkun.API.ApiResponse;
import com.example.tamakkun.DTO_Out.SpecialistRatingDTO;
import com.example.tamakkun.Model.MyUser;
import com.example.tamakkun.Model.Review;
import com.example.tamakkun.Service.ReviewService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/tamakkun-system/review")
@RequiredArgsConstructor
public class ReviewController {

    private final ReviewService reviewService;

    @GetMapping("/get-All-reviews")
    public ResponseEntity getAllReviews(@AuthenticationPrincipal MyUser user){
        return ResponseEntity.status(200).body(reviewService.getAllReviews());
    }

    @GetMapping("/reviews/byCentre")
    public ResponseEntity getReviewsByCentre(@AuthenticationPrincipal MyUser user){
        return ResponseEntity.status(200).body(reviewService.getReviewsByCentre(user.getId()));
    }

    @PostMapping("new-review/booking/{booking_id}")
    public ResponseEntity newReview(@AuthenticationPrincipal MyUser user, @PathVariable Integer booking_id, @RequestBody @Valid Review review) {
        reviewService.newReview(user.getId(), booking_id, review);
        return ResponseEntity.status(200).body(new ApiResponse("review added"));
    }

    @GetMapping("/top-three-specialists")
    public ResponseEntity getTopThreeRatedSpecialists(@AuthenticationPrincipal MyUser user) {
        List<SpecialistRatingDTO> topSpecialists = reviewService.getTopThreeRatedSpecialists(user.getId());
        return ResponseEntity.ok(topSpecialists);
    }


}
