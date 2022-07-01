package com.yoonbin.triple.club.mileage.review.service;

import com.yoonbin.triple.club.mileage.review.domain.Review;
import com.yoonbin.triple.club.mileage.review.domain.ReviewDto;
import com.yoonbin.triple.club.mileage.review.repository.ReviewRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.LinkedList;
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
        List<Review> reviews = new LinkedList<>();
        Review review = reviewRepository.save(Review.builder()
                .content("getReviewsByPlaceIdTest")
                .attachedPhotoIds(List.of(new String[]{"image1.jpg",
                        "image2.png", "image3.jpg"}))
                .placeId("1641ac95-da05-4e76-aecd-8b524d5e408f")
                .userId("42ab933f-2f37-4131-a784-8e4f889418c9")
                .build());

        reviews.add(review);

        for (Review r : reviews) {
            assertThat(r.getPlaceId()).isEqualTo("1641ac95-da05-4e76-aecd-8b524d5e408f");
        }
    }

    @Test
    void getReviewsByUserIdTest() {
        List<Review> reviews = new LinkedList<>();
        Review review = reviewRepository.save(Review.builder()
                .content("getReviewsByUserIdTest")
                .attachedPhotoIds(List.of(new String[]{"image1.jpg",
                        "image2.png", "image3.jpg"}))
                .placeId("1641ac95-da05-4e76-aecd-8b524d5e408f")
                .userId("73d193ed-5158-499b-9bd3-d5763e57bb42")
                .build());

        reviews.add(review);

        for (Review r : reviews) {
            assertThat(r.getUserId()).isEqualTo("73d193ed-5158-499b-9bd3-d5763e57bb42");
        }
    }
    @Test
    void getUserIdsByPlaceIdTest() {
        List<Review> reviews = new LinkedList<>();
        Review review = reviewRepository.save(Review.builder()
                .content("getUserIdsByPlaceIdTest")
                .attachedPhotoIds(List.of(new String[]{"image1.jpg",
                        "image2.png", "image3.jpg"}))
                .placeId("405ac2ad-b54e-4207-8f58-1ccba3848ead")
                .userId("73d193ed-5158-499b-9bd3-d5763e57bb42")
                .build());

        reviews.add(review);

        List<String> userIds = reviewService.getUserIdsByPlaceId("405ac2ad-b54e-4207-8f58-1ccba3848ead");

        for (String userId : userIds) {
            for (Review r : reviewService.getReviewsByUserId(userId)){
                assertThat(r.getPlaceId()).isEqualTo("405ac2ad-b54e-4207-8f58-1ccba3848ead");
            }
        }
    }

    @Test
    void checkPhotoTest() {
        Review review = Review.builder()
                .content("checkPhotoTest")
                .attachedPhotoIds(List.of(new String[]{"image1.jpg",
                        "image2.png", "image3.jpg"}))
                .placeId("65553057-d3de-4c34-9b19-6ffff381c47f")
                .userId("b0e96d95-1c85-416d-bc35-8b3017dd5a65")
                .build();

        if(review.getAttachedPhotoIds().isEmpty()){
            assertThat(reviewService.checkPhoto(review)).isFalse();
        }else{
            assertThat(reviewService.checkPhoto(review)).isTrue();
        }
    }

    @Test
    void checkContentTest() {
        Review review = reviewRepository.save(Review.builder()
                .content("checkContentTest")
                .attachedPhotoIds(List.of(new String[]{"image1.jpg",
                        "image2.png", "image3.jpg"}))
                .placeId("6044507f-d303-4d41-bec8-b4ebce6d8da3")
                .userId("42ab933f-2f37-4131-a784-8e4f889418c9")
                .build());

        if(review.getContent().isEmpty()){
            assertThat(reviewService.checkContent(review)).isFalse();
        }else{
            assertThat(reviewService.checkContent(review)).isTrue();
        }
    }
    @Test
    void visitedCheckTest() {
        Review review = reviewRepository.save(Review.builder()
                .content("사장님이 친절하고 음식이 맛있어요.")
                .attachedPhotoIds(List.of(new String[]{"image1.jpg",
                        "image2.png", "image3.jpg"}))
                .placeId("4443a517-2147-4f42-9ec7-96281035d6af")
                .userId("42ab933f-2f37-4131-a784-8e4f889418c9")
                .build());

        boolean ck = reviewService.visitedCheck(review);
        if(reviewService.getUserIdsByPlaceId(review.getPlaceId()).isEmpty()){
            assertThat(ck).isFalse();
        }else{
            assertThat(ck).isTrue();
        }
    }

    @Test
    void checkPlaceTest() {
        Review review = Review.builder()
                .content("checkPlaceTest")
                .attachedPhotoIds(List.of(new String[]{"image1.jpg",
                        "image2.png", "image3.jpg"}))
                .placeId("69da8289-6d34-45f5-9525-2b7539bf4eb0")
                .userId("b0e96d95-1c85-416d-bc35-8b3017dd5a65")
                .build();

        if(reviewService.visitedCheck(review)){
            assertThat(reviewService.checkPlace(review)).isFalse();
        }else{
            assertThat(reviewService.checkPlace(review)).isTrue();
        }
    }

    @Test
    void checkAmountTest() {
        int amount = 0;
        Review review = Review.builder()
                .content("checkAmountTest")
                .attachedPhotoIds(List.of(new String[]{"image1.jpg",
                        "image2.png", "image3.jpg"}))
                .placeId("64772458-8504-5ac8-9315-37b2c720c7e9")
                .userId("b0e96d95-1c85-416d-bc35-8b3017dd5a65")
                .build();

        if (reviewService.checkPhoto(review)) amount++;
        if (reviewService.checkContent(review)) amount++;
        if(amount > 0){ if (reviewService.checkPlace(review)) amount++;}

        assertThat(reviewService.checkAmount(review).getKey()).isEqualTo(amount);
    }

    @Test
    void getReviewsTest() {
        assertThat(reviewService.getReviews()).isNotNull();
    }

    @Test
    void insertReviewTest() {
        ReviewDto reviewDto = reviewService.insertReview(Review.builder()
                .content("insertReviewTest")
                .attachedPhotoIds(List.of(new String[]{"image1.jpg",
                        "image2.png", "image3.jpg"}))
                .placeId("0c1a7704-ceb3-4fef-90c5-2277dffc6cc2")
                .userId("b0e96d95-1c85-416d-bc35-8b3017dd5a65")
                .build());
        assertNotNull(reviewDto.getReviewId());
    }
}