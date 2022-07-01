package com.yoonbin.triple.club.mileage.review.controller;

import com.yoonbin.triple.club.mileage.review.domain.Review;
import com.yoonbin.triple.club.mileage.review.domain.ReviewDto;
import com.yoonbin.triple.club.mileage.review.service.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ReviewController {
    private final ReviewService reviewService;

    @PutMapping("/events")
    public ReviewDto saveReview(@RequestBody Review review){
        return reviewService.insertReview(review);
    }
}

