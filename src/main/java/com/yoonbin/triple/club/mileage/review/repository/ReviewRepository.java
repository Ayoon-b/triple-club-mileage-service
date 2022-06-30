package com.yoonbin.triple.club.mileage.review.repository;

import com.yoonbin.triple.club.mileage.review.domain.Review;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReviewRepository extends JpaRepository <Review, String> {

}


