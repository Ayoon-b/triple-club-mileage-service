package com.yoonbin.triple.club.mileage.point.service;

import com.yoonbin.triple.club.mileage.point.domain.Point;
import com.yoonbin.triple.club.mileage.point.domain.PointDto;
import com.yoonbin.triple.club.mileage.point.repository.PointRepository;
import com.yoonbin.triple.club.mileage.review.domain.Review;
import com.yoonbin.triple.club.mileage.review.service.ReviewService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class PointServiceTest {
    @Autowired
    PointService pointService;
    @Autowired
    PointRepository pointRepository;
    @Autowired
    ReviewService reviewService;

    @Test
    void addPointTest() {
        PointDto pointDto = PointDto.builder()
                .type("REVIEW")
                .content("짱")
                .attachedPhotoIds(List.of(new String[]{"image1.jpg"}))
                .action("ADD")
                .reviewId("8f77d8b9-4c76-41a6-8921-d3a647f2143e")
                .placeId("5044507f-d303-4d41-bec8-b4ebce6d8da3")
                .userId("ec202eb6-c3a2-450b-9840-0fe835d4c1c1")
                .build();
        pointService.addPoint(pointDto);
        assertNotNull(pointRepository.findByReviewId(pointDto.getReviewId()));
    }

    @Test
    void modPointTest() {
        Review review = Review.builder()
                .reviewId("3dc9946f-1ccd-48ac-b1fa-15d36e19048e")
                .content("modPointTest")
                .attachedPhotoIds(List.of(new String[]{"image1.jpg", "image2.png", "image3.jpg"}))
                .placeId("5044507f-d303-4d41-bec8-b4ebce6d8da3")
                .userId("42ab933f-2f37-4131-a784-8e4f889418c9")
                .build();
        reviewService.saveReview(review);

        PointDto pointDto = PointDto.builder()
                .type("REVIEW")
                .content("짱")
                .attachedPhotoIds(List.of(new String[]{"image1.jpg"}))
                .action("MOD")
                .reviewId("3dc9946f-1ccd-48ac-b1fa-15d36e19048e")
                .placeId("5044507f-d303-4d41-bec8-b4ebce6d8da3")
                .userId("42ab933f-2f37-4131-a784-8e4f889418c9")
                .build();
        pointService.modPoint(pointDto);
        Assertions.assertThat(reviewService.getReviewByReviewId(pointDto.getReviewId()).getContent()).isEqualTo(pointDto.getContent());
    }

    @Test
    void deletePointTest() {
        Review review = Review.builder()
                .reviewId("1821cb95-b85c-4464-8ce3-c14d646e886a")
                .content("deletePointTest")
                .attachedPhotoIds(List.of(new String[]{"image1.jpg"}))
                .placeId("c87af3bf-5f2d-4faa-821e-3fc45b0280a0")
                .userId("42ab933f-2f37-4131-a784-8e4f889418c9")
                .build();

        reviewService.insertReview(review);

        PointDto pointDto = PointDto.builder()
                .type("REVIEW")
                .content("짱")
                .attachedPhotoIds(List.of(new String[]{"image1.jpg"}))
                .action("DELETE")
                .reviewId("1821cb95-b85c-4464-8ce3-c14d646e886a")
                .placeId("c87af3bf-5f2d-4faa-821e-3fc45b0280a0")
                .userId("42ab933f-2f37-4131-a784-8e4f889418c9")
                .build();
        PointDto deletePoint = pointService.deletePoint(pointDto);
        System.out.println(deletePoint);

        int amount = 0;
        for (Point point : pointRepository.findByPlaceId(pointDto.getPlaceId())) {
            if (point.getUserId().equals(pointDto.getUserId())){
                amount+=point.getAmount();
            }
        }
        Assertions.assertThat(amount).isEqualTo(0);
    }

    @Test
    void getPointsTest() {
        Assertions.assertThat(pointService.getPoints()).isNotNull();
    }
}
