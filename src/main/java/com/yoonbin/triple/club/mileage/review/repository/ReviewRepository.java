package com.yoonbin.triple.club.mileage.review.repository;

import com.yoonbin.triple.club.mileage.review.domain.Review;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReviewRepository extends JpaRepository <Review, String> {

    List<Review> findByPlaceIdOrderByCreatedAt(String placeId);
    List<Review> findByUserId(String userId);
}


