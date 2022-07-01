package com.yoonbin.triple.club.mileage.point.service;

import com.yoonbin.triple.club.mileage.point.domain.Point;
import com.yoonbin.triple.club.mileage.point.domain.PointDto;
import com.yoonbin.triple.club.mileage.point.repository.PointRepository;
import com.yoonbin.triple.club.mileage.review.domain.Review;
import com.yoonbin.triple.club.mileage.review.service.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PointService {
    @Autowired
    PointRepository pointRepository;
    @Autowired
    ReviewService reviewService;

    @Transactional
    public PointDto addPoint(PointDto pointDto) {
        Review review = Review.builder()
                .reviewId(pointDto.getReviewId())
                .attachedPhotoIds(pointDto.getAttachedPhotoIds())
                .content(pointDto.getContent())
                .placeId(pointDto.getPlaceId())
                .userId(pointDto.getUserId())
                .build();
        reviewService.insertReview(review);
        return pointDto;
    }

    @Transactional
    public PointDto modPoint(PointDto pointDto) {
        Review review = Review.builder()
                .reviewId(pointDto.getReviewId())
                .attachedPhotoIds(pointDto.getAttachedPhotoIds())
                .content(pointDto.getContent())
                .placeId(pointDto.getPlaceId())
                .build();
        reviewService.updateReview(review.getReviewId(), review);
        return pointDto;
    }

    @Transactional
    public PointDto deletePoint(PointDto pointDto) {
        reviewService.deleteReview(pointDto.getReviewId());
        return pointDto;
    }

    public List<Point> getPoints() {
        return pointRepository.findAll();
    }
}
