package com.yoonbin.triple.club.mileage.review.controller;

import com.yoonbin.triple.club.mileage.review.domain.Review;
import com.yoonbin.triple.club.mileage.review.service.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class ReviewController {
    private final ReviewService reviewService;

    @PutMapping("/review")
    public Review saveReview(@RequestBody Review review){
        return reviewService.insertReview(review);
    }

    @PutMapping("/review/{id}")
    public Review updateReview(@RequestParam String id, @RequestBody Review review) {
        return reviewService.updateReview(id, review);
    }

    @DeleteMapping("/review/{id}")
    public void deleteReview(@RequestParam String id) {
        reviewService.deleteReview(id);
    }
}

