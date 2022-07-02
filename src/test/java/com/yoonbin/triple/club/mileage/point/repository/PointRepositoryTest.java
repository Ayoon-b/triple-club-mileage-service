package com.yoonbin.triple.club.mileage.point.repository;

import com.yoonbin.triple.club.mileage.point.domain.Point;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import javax.transaction.Transactional;
import java.util.UUID;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Transactional
class PointRepositoryTest {
    @Autowired
    PointRepository pointRepository;

    @Test
    void findByReviewIdTest() {
        Point point = pointRepository.save(Point.builder()
                .reviewId(UUID.randomUUID().toString())
                .userId(UUID.randomUUID().toString())
                .placeId(UUID.randomUUID().toString())
                .remarks(UUID.randomUUID().toString())
                .amount(0)
                .build());

        Assertions.assertThat(pointRepository.findByReviewId(point.getReviewId())).contains(point);
    }
}