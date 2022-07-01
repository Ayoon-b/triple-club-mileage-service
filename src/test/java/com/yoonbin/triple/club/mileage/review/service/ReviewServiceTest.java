package com.yoonbin.triple.club.mileage.review.service;

import com.yoonbin.triple.club.mileage.review.domain.Review;
import com.yoonbin.triple.club.mileage.review.domain.ReviewDto;
import com.yoonbin.triple.club.mileage.review.repository.ReviewRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class ReviewServiceTest {
    @Autowired
    ReviewService reviewService;
    @Autowired
    ReviewRepository reviewRepository;

    @Test
    void getReviewsByPlaceIdTest() {
        List<Review> review = reviewService.getReviewsByPlaceId("49fa8287-6d24-45f5-9525-2b7539bf4eb0");
        for (Review r : review) {
            assertThat(r.getPlaceId()).isEqualTo("49fa8287-6d24-45f5-9525-2b7539bf4eb0");
        }
    }

    @Test
    void getReviewsByUserIdTest() {
        List<Review> review = reviewService.getReviewsByUserId("b0e96d94-1c85-416d-bc35-8b3017dd5a65");
        for (Review r : review) {
            assertThat(r.getUserId()).isEqualTo("b0e96d94-1c85-416d-bc35-8b3017dd5a65");
        }
    }
    @Test
    void getUserIdsByPlaceIdTest() {
        List<String> userIds = reviewService.getUserIdsByPlaceId("b0e96d94-1c85-416d-bc35-8b3017dd5a65");
        for (String userId : userIds) {
            for (Review r : reviewService.getReviewsByUserId(userId)){
                assertThat(r.getPlaceId()).isEqualTo("b0e96d94-1c85-416d-bc35-8b3017dd5a65");
            }
        }
    }

    @Test
    void checkPhotoTest() {
        Review review = reviewRepository.findById("5e5ac8c0-9e5e-484a-8cb3-d84230d5d93f").get();
        if(review.getAttachedPhotoIds().isEmpty()){
            assertThat(reviewService.checkPhoto(review).getKey()).isEqualTo(0);
        }else{
            assertThat(reviewService.checkPhoto(review).getKey()).isEqualTo(1);
        }
    }

    @Test
    void checkContentTest() {
        Review review = reviewRepository.findById("5e5ac8c0-9e5e-484a-8cb3-d84230d5d93f").get();
        if(review.getContent().isEmpty()){
            assertThat(reviewService.checkContent(review).getKey()).isEqualTo(0);
        }else{
            assertThat(reviewService.checkContent(review).getKey()).isEqualTo(1);
        }
    }
    @Test
    void visitedCheckTest() {
        Review review = Review.builder()
                .content("사장님이 친절하고 음식이 맛있어요.")
                .attachedPhotoIds(List.of(new String[]{"image1.jpg",
                        "image2.png", "image3.jpg"}))
                .placeId("5044507f-d303-4d41-bec8-b4ebce6d8da3")
                .userId("42ab933f-2f37-4131-a784-8e4f889418c9")
                .build();

        boolean ck = reviewService.visitedCheck(review);
        if(reviewService.getUserIdsByPlaceId(review.getPlaceId()).isEmpty()){
            assertThat(ck).isFalse();
        }else{
            assertThat(ck).isTrue();
        }
    }

    @Test
    void checkPlaceTest() {
        Review review = reviewRepository.findById("5e5ac8c0-9e5e-484a-8cb3-d84230d5d93f").get();
        if(reviewService.visitedCheck(review)){
            assertThat(reviewService.checkPlace(review).getKey()).isEqualTo(0);
        }else{
            assertThat(reviewService.checkPlace(review).getKey()).isEqualTo(1);
        }
    }

    @Test
    void checkAmountTest() {
        Review review = reviewRepository.findById("5e5ac8c0-9e5e-484a-8cb3-d84230d5d93f").get();
        int amount = 0;

        if (reviewService.checkPhoto(review).getKey() > 0) amount++;
        if (reviewService.checkContent(review).getKey() > 0) amount++;
        if (reviewService.checkPlace(review).getKey() > 0) amount++;

        assertThat(reviewService.checkAmount(review).getKey()).isEqualTo(amount);
    }

    @Test
    void getReviewsTest() {
        assertThat(reviewService.getReviews()).isNotNull();
    }

    @Test
    void insertReviewTest() {
        ReviewDto reviewDto = reviewService.insertReview(Review.builder()
                .content("0701 리뷰 추가 테스트")
                .attachedPhotoIds(List.of(new String[]{"image1.jpg",
                        "image2.png", "image3.jpg"}))
                .placeId("69da8289-6d24-45f5-9525-2b7539bf4eb0")
                .userId("a0e96d95-1c85-416d-bc35-8b3017dd5a65")
                .build());
        assertNotNull(reviewDto.getReviewId());
    }
}