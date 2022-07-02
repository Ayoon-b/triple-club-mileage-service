package com.yoonbin.triple.club.mileage.review.service;

import com.yoonbin.triple.club.mileage.point.service.PointService;
import com.yoonbin.triple.club.mileage.review.domain.Review;
import com.yoonbin.triple.club.mileage.review.repository.ReviewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@RequiredArgsConstructor
public class ReviewService {
    private final ReviewRepository reviewRepository;
    private PointService pointService;

    @Autowired
    public void setPointService(@Lazy PointService pointService) {
        this.pointService = pointService;
    }

    public boolean visitedCheck(Review review){
        return reviewRepository.existsByUserIdAndPlaceId(review.getUserId(), review.getPlaceId());
    }

    public boolean hasContent(Review review){
        return review.getContent().length() > 0;
    }

    public boolean hasPhoto(Review review){
        return !review.getAttachedPhotoIds().isEmpty();
    }

    public boolean isFirstReview(String placeId){
        return !reviewRepository.existsByPlaceId(placeId);
    }


    @Transactional
    public Review insertReview(Review review) {
        if (visitedCheck(review)) { throw new IllegalStateException("해당 장소에 이미 작성된 리뷰가 존재합니다."); }
        if (!hasContent(review) && !hasPhoto(review)) {
            throw new IllegalStateException("텍스트와 사진이 작성되지 않아 리뷰로 등록되지 않습니다.");
        }
        pointService.insert(review);
        reviewRepository.save(review);
        return review;
    }

    @Transactional
    public Review updateReview(Review review) {
        if(!hasContent(review) && !hasPhoto(review)) { throw new IllegalStateException("내용이 비어있습니다."); }
        Review preReview = reviewRepository.findById(review.getReviewId())
                .orElseThrow(() -> new IllegalStateException("해당 리뷰가 존재하지 않습니다."));

        review.setCreatedAt(preReview.getCreatedAt());

        pointService.update(preReview, review);
        reviewRepository.save(review);
        return review;
    }

    @Transactional
    public void deleteReview(Review review) {
        Review found = reviewRepository.findById(review.getReviewId())
                .orElseThrow(() -> new IllegalStateException("해당 리뷰가 존재하지 않습니다."));
        reviewRepository.delete(found);
        pointService.deleteReview(found);
    }
}

