package com.yoonbin.triple.club.mileage.point.repository;

import com.yoonbin.triple.club.mileage.point.domain.Point;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PointRepository extends JpaRepository<Point, Integer> {
    List<Point> findByReviewId(String reviewId);
    List<Point> findByPlaceId(String placeId);
}

