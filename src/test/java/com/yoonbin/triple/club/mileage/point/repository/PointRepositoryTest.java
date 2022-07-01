package com.yoonbin.triple.club.mileage.point.repository;

import com.yoonbin.triple.club.mileage.point.domain.Point;
import com.yoonbin.triple.club.mileage.review.repository.ReviewRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class PointRepositoryTest {
    @Autowired
    PointRepository pointRepository;

    @Autowired
    ReviewRepository reviewRepository;

    @Test
    void saveTest() throws Throwable{

        Point point = Point.builder()
                .amount(1)
                .remarks("리뷰 작성")
                .reviewId("3dc9946f-1ccd-48ac-b1fa-15d36e19048e")
                .placeId("5044507f-d303-4d41-bec8-b4ebce6d8da3")
                .userId("42ab933f-2f37-4131-a784-8e4f889418c9")
                .build();

        point = pointRepository.save(point);
        assertNotNull(point.getId());

    }

    @Test
    void findByReviewIdTest() {
        Point point = Point.builder()
                .amount(1)
                .remarks("findAmountByReviewIdTest")
                .reviewId("3dc9946f-1ccd-48ac-b1fa-15d36e19048e")
                .placeId("5044507f-d303-4d41-bec8-b4ebce6d8da3")
                .userId("42ab933f-2f37-4131-a784-8e4f889418c9")
                .build();
        pointRepository.save(point);

        assertNotNull(pointRepository.findByReviewId("3dc9946f-1ccd-48ac-b1fa-15d36e19048e"));
    }
}
