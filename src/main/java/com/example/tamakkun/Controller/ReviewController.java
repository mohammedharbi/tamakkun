package com.example.tamakkun.Controller;

import com.example.tamakkun.Service.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/tamakkun/review")
@RequiredArgsConstructor
public class ReviewController {

    private final ReviewService reviewService;




}
