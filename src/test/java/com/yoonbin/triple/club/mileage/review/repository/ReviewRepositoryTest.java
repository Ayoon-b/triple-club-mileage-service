package com.yoonbin.triple.club.mileage.review.repository;

import com.yoonbin.triple.club.mileage.review.domain.Review;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class ReviewRepositoryTest {
    @Autowired
    ReviewRepository reviewRepository;

    @Test
    void saveTest() throws Throwable{

        Review review1 = Review.builder()
                .content("최고의 장소 ! 추천합니다.")
                .attachedPhotoIds(List.of(new String[]{}))
                .placeId("61560b36-59e1-450a-8d36-362b59ef71d6")
                .userId("9f98f5b3-998a-4b45-bc38-2b6151426172")
                .build();

        Review review2 = Review.builder()
                .content("")
                .attachedPhotoIds(List.of(new String[]{"image1.jpg"}))
                .placeId("61560b36-59e1-450a-8d36-362b59ef71d6")
                .userId("8f98f5b3-998a-4b45-bc38-2b6151426173")
                .build();

        Review review3 = Review.builder()
                .content("추천")
                .attachedPhotoIds(List.of(new String[]{"image1.jpg",
                        "image2.png"}))
                .placeId("test")
                .userId("test")
                .build();

        review1 = reviewRepository.save(review1);
        review2 = reviewRepository.save(review2);
        review3 = reviewRepository.save(review3);

        assertNotNull(review1.getReviewId());
        assertNotNull(review2.getReviewId());
        assertNotNull(review3.getReviewId());

    }
}
