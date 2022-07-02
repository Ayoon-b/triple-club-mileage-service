package com.yoonbin.triple.club.mileage.point.service;

import com.yoonbin.triple.club.mileage.review.domain.Review;
import com.yoonbin.triple.club.mileage.review.service.ReviewService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;

import javax.transaction.Transactional;
import java.util.List;
import java.util.UUID;

@SpringBootTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Transactional
class PointServiceTest {
    @Autowired
    PointService pointService;
    @Autowired
    ReviewService reviewService;

    @Test
    void getPointsTest() {
        Review review = Review.builder()
                .reviewId(UUID.randomUUID().toString())
                .attachedPhotoIds(List.of(UUID.randomUUID().toString()))
                .placeId(UUID.randomUUID().toString())
                .content(UUID.randomUUID().toString())
                .userId(UUID.randomUUID().toString())
                .build();
        reviewService.insertReview(review);

        Review review2 = Review.builder()
                .reviewId(review.getReviewId())
                .attachedPhotoIds(review.getAttachedPhotoIds())
                .placeId(review.getPlaceId())
                .content("")
                .userId(review.getUserId())
                .build();
        reviewService.updateReview(review2);

        reviewService.deleteReview(review);

        Assertions.assertThat(pointService.getPoints()).hasSize(3);
    }
}