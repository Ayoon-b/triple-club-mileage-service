package com.yoonbin.triple.club.mileage.point.controller;

import com.yoonbin.triple.club.mileage.point.domain.Point;
import com.yoonbin.triple.club.mileage.point.domain.PointDto;
import com.yoonbin.triple.club.mileage.point.service.PointService;
import com.yoonbin.triple.club.mileage.review.domain.Review;
import com.yoonbin.triple.club.mileage.review.service.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class PointController {
    private final PointService pointService;
    private final ReviewService reviewService;

    @PostMapping("/events")
    public PointDto savePoint(@RequestBody PointDto pointDto){
        Review review = Review.builder()
                .reviewId(pointDto.getReviewId())
                .userId(pointDto.getUserId())
                .placeId(pointDto.getPlaceId())
                .content(pointDto.getContent())
                .attachedPhotoIds(pointDto.getAttachedPhotoIds())
                .build();

        if(pointDto.getAction().equals("ADD")){
            reviewService.insertReview(review);
        }else if(pointDto.getAction().equals("MOD")){
            reviewService.updateReview(review);
        }else if(pointDto.getAction().equals("DELETE")){
            reviewService.deleteReview(review);
        }else {
            throw new IllegalArgumentException("정확한 Action이 아닙니다.");
        }
        return pointDto;
    }

    @GetMapping("/points")
    public List<Point> getPoints(){
        return pointService.getPoints();
    }

}
