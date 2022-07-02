package com.yoonbin.triple.club.mileage.review.service;

import com.yoonbin.triple.club.mileage.point.repository.PointRepository;
import com.yoonbin.triple.club.mileage.point.service.PointService;
import com.yoonbin.triple.club.mileage.review.domain.Review;
import com.yoonbin.triple.club.mileage.review.repository.ReviewRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;

import javax.transaction.Transactional;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

@SpringBootTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Transactional
class ReviewServiceTest {
    @Autowired
    ReviewService reviewService;

    @Autowired
    ReviewRepository reviewRepository;

    @Autowired
    PointRepository pointRepository;

    @Autowired
    PointService pointService;

    private Review review() {
        return Review.builder()
                .reviewId(UUID.randomUUID().toString())
                .attachedPhotoIds(List.of(UUID.randomUUID().toString()))
                .placeId(UUID.randomUUID().toString())
                .content(UUID.randomUUID().toString())
                .userId(UUID.randomUUID().toString())
                .build();
    }

    @Test
    void visitedCheckTest() {
        Review review = reviewRepository.save(review());
        Assertions.assertThat(reviewService.visitedCheck(review)).isTrue();
    }

    @Test
    void isFirstReviewTest() {
        Review review = reviewRepository.save(review());
        Assertions.assertThat(reviewService.isFirstReview(review.getPlaceId())).isFalse();
    }

    @Test
    void insertReviewTest() {
        Review review = review();
        review.setContent("");
        review.setAttachedPhotoIds(Collections.emptyList());

        Assertions.assertThatThrownBy(() -> reviewService.insertReview(review))
                .isInstanceOf(IllegalStateException.class);

        Review review2 = review();
        reviewService.insertReview(review2);
        Assertions.assertThat(reviewRepository.findById(review2.getReviewId())
                        .map(Review::getReviewId).orElse(null))
                        .isEqualTo(review2.getReviewId());
        Assertions.assertThat(pointRepository.findByReviewId(review2.getReviewId())).isNotEmpty();
        Assertions.assertThat(pointService.sumOfPointByReview(review2)).isEqualTo(3);

        Review review3 = review();
        review3.setContent("");
        reviewService.insertReview(review3);
        Assertions.assertThat(reviewRepository.findById(review3.getReviewId())
                        .map(Review::getReviewId).orElse(null))
                .isEqualTo(review3.getReviewId());
        Assertions.assertThat(pointRepository.findByReviewId(review3.getReviewId())).isNotEmpty();
        Assertions.assertThat(pointService.sumOfPointByReview(review3)).isEqualTo(2);

        Review review4 = review();
        review4.setPlaceId(review2.getPlaceId());
        reviewService.insertReview(review4);
        Assertions.assertThat(reviewRepository.findById(review4.getReviewId())
                        .map(Review::getReviewId).orElse(null))
                .isEqualTo(review4.getReviewId());
        Assertions.assertThat(pointRepository.findByReviewId(review4.getReviewId())).isNotEmpty();
        Assertions.assertThat(pointService.sumOfPointByReview(review4)).isEqualTo(2);

        Review review5 = review();
        review5.setPlaceId(review3.getPlaceId());
        review5.setContent("");
        reviewService.insertReview(review5);
        Assertions.assertThat(reviewRepository.findById(review5.getReviewId())
                        .map(Review::getReviewId).orElse(null))
                .isEqualTo(review5.getReviewId());
        Assertions.assertThat(pointRepository.findByReviewId(review5.getReviewId())).isNotEmpty();
        Assertions.assertThat(pointService.sumOfPointByReview(review5)).isEqualTo(1);
    }

    @Test
    void updateReviewTest() {
        Review review = review();
        reviewService.insertReview(review);
        Assertions.assertThat(pointService.sumOfPointByReview(review)).isEqualTo(3);

        Review review2 = Review.builder()
                .reviewId(review.getReviewId())
                .placeId(review.getPlaceId())
                .userId(review.getUserId())
                .content("not empty")
                .attachedPhotoIds(Collections.emptyList())
                .build();
        reviewService.updateReview(review2);
        Assertions.assertThat(pointService.sumOfPointByReview(review2)).isEqualTo(2);

        Review review3 = Review.builder()
                .reviewId(review.getReviewId())
                .placeId(review.getPlaceId())
                .userId(review.getUserId())
                .content("")
                .attachedPhotoIds(Collections.emptyList())
                .build();
        Assertions.assertThatThrownBy(() -> reviewService.updateReview(review3))
                .isInstanceOf(IllegalStateException.class);

        Review review4 = Review.builder()
                .reviewId(review.getReviewId())
                .placeId(review.getPlaceId())
                .userId(review.getUserId())
                .content("")
                .attachedPhotoIds(List.of("image"))
                .build();
        reviewService.updateReview(review4);
        Assertions.assertThat(pointService.sumOfPointByReview(review4)).isEqualTo(2);

        Review review5 = Review.builder()
                .reviewId(review.getReviewId())
                .placeId(review.getPlaceId())
                .userId(review.getUserId())
                .content("not empty")
                .attachedPhotoIds(List.of("image"))
                .build();
        reviewService.updateReview(review5);
        Assertions.assertThat(pointService.sumOfPointByReview(review5)).isEqualTo(3);

    }

    @Test
    void deleteReviewTest() {
        Review review = review();
        reviewService.insertReview(review);
        Assertions.assertThat(pointService.sumOfPointByReview(review)).isEqualTo(3);
        reviewService.deleteReview(review);
        Assertions.assertThat(pointService.sumOfPointByReview(review)).isEqualTo(0);
    }
}
