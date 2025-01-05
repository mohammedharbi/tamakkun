package com.example.tamakkun.Controller;

import com.example.tamakkun.API.ApiResponse;
import com.example.tamakkun.DTO_Out.SpecialistRatingDTO;
import com.example.tamakkun.Model.Review;
import com.example.tamakkun.Service.ReviewService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/tamakkun-system/review")
@RequiredArgsConstructor
public class ReviewController {

    private final ReviewService reviewService;



    @GetMapping("/reviews/byCentre/{centreId}")
    public ResponseEntity getReviewsByCentre(@PathVariable Integer centreId){
        return ResponseEntity.status(200).body(reviewService.getReviewsByCentre(centreId));
    }

    @PostMapping("/add-review/{parent_id}/{centre_id}/{specialist_id}")
    public ResponseEntity addReviewCentre(@PathVariable Integer parent_id, @PathVariable Integer centre_id, @PathVariable Integer specialist_id, @RequestBody @Valid Review review){
        reviewService.addReviewCentre(parent_id, centre_id, specialist_id, review);
        return ResponseEntity.status(200).body(new ApiResponse("Review added successfully!"));

    }
    @GetMapping("/top-three-specialists")
    public ResponseEntity getTopRatedSpecialists() {
        List<SpecialistRatingDTO> topSpecialists = reviewService.getTopThreeRatedSpecialists();
        return ResponseEntity.ok(topSpecialists);
    }


}
