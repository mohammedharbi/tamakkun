package com.example.tamakkun.Controller;

import com.example.tamakkun.API.ApiResponse;
import com.example.tamakkun.Model.Review;
import com.example.tamakkun.Service.ReviewService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/tamakkun-system/review")
@RequiredArgsConstructor
public class ReviewController {

    private final ReviewService reviewService;

    @PostMapping("new-review/parent/{parent_id}/booking/{booking_id}")
    public ResponseEntity newReview(@PathVariable Integer parent_id, @PathVariable Integer booking_id, @RequestBody @Valid Review review) {
        reviewService.newReview(parent_id,booking_id, review);
        return ResponseEntity.status(200).body(new ApiResponse("review added"));
    }

}
