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

        Review review = Review.builder()
                .content("사장님이 친절하고 음식이 맛있어요.")
                .attachedPhotoIds(List.of(new String[]{"image1.jpg",
                        "image2.png", "image3.jpg"}))
                .placeId("5044507f-d303-4d41-bec8-b4ebce6d8da3")
                .userId("42ab933f-2f37-4131-a784-8e4f889418c9")
                .build();

        review = reviewRepository.save(review);
        assertNotNull(review.getReviewId());

    }
}
