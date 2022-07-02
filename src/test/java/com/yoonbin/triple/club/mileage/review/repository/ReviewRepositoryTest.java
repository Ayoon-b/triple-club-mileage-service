package com.yoonbin.triple.club.mileage.review.repository;

import com.yoonbin.triple.club.mileage.review.domain.Review;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import javax.transaction.Transactional;
import java.util.List;
import java.util.UUID;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Transactional
class ReviewRepositoryTest {
    @Autowired
    ReviewRepository reviewRepository;

    @Test
    void existsByPlaceIdTest() {
        Review review = reviewRepository.save(Review.builder()
                .attachedPhotoIds(List.of(UUID.randomUUID().toString()))
                .placeId(UUID.randomUUID().toString())
                .content(UUID.randomUUID().toString())
                .userId(UUID.randomUUID().toString())
                .build());

        Assertions.assertThat(reviewRepository.existsByPlaceId(review.getPlaceId())).isTrue();
    }

    @Test
    void existsByUserIdAndPlaceIdTest() {
        Review review = reviewRepository.save(Review.builder()
                .attachedPhotoIds(List.of(UUID.randomUUID().toString()))
                .placeId(UUID.randomUUID().toString())
                .content(UUID.randomUUID().toString())
                .userId(UUID.randomUUID().toString())
                .build());

        Assertions.assertThat(reviewRepository.existsByUserIdAndPlaceId(review.getUserId(), review.getPlaceId())).isTrue();
    }
}
