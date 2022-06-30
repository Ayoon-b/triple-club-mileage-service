package com.yoonbin.triple.club.mileage.point.repository;

import com.yoonbin.triple.club.mileage.point.domain.Point;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PointRepository extends JpaRepository<Point, Integer> {

}

