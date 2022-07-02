package com.yoonbin.triple.club.mileage.point.service;

import com.yoonbin.triple.club.mileage.point.domain.Point;
import com.yoonbin.triple.club.mileage.point.repository.PointRepository;
import com.yoonbin.triple.club.mileage.review.domain.Review;
import com.yoonbin.triple.club.mileage.review.service.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class PointService {
    private final PointRepository pointRepository;
    private ReviewService reviewService;

    @Autowired
    public void setReviewService(ReviewService reviewService) {
        this.reviewService = reviewService;
    }

    private Point getPoint(Review review, Map.Entry<Integer, String> amount) {
        return Point.builder()
                .reviewId(review.getReviewId())
                .remarks(amount.getValue())
                .amount(amount.getKey())
                .placeId(review.getPlaceId())
                .userId(review.getUserId())
                .build();
    }

    public int sumOfPointByReview(Review review) {
        return pointRepository.findByReviewId(review.getReviewId()).stream().mapToInt(Point::getAmount).sum();
    }

    public Point deleteReview(Review review) {
        Point point = Point.builder()
                .reviewId(review.getReviewId())
                .amount(-sumOfPointByReview(review))
                .remarks("리뷰 삭제")
                .placeId(review.getPlaceId())
                .userId(review.getUserId())
                .build();
        return pointRepository.save(point);
    }

    @Transactional
    public Point insert(Review review) {
        return pointRepository.save(getPoint(review, checkAmount(review)));
    }

    @Transactional
    public Point update(Review preReview, Review review) {
        return pointRepository.save(getPoint(review, updateAmount(preReview, review)));
    }

    public Map.Entry<Integer, String> updateAmount(Review preReview, Review review){
        int amount = 0;
        List<String> remarks = new LinkedList<>();

        if (review.getContent().isEmpty() && !preReview.getContent().isEmpty()) {
            amount--;
            remarks.add("텍스트 삭제");
        } else if (!review.getContent().isEmpty() && preReview.getContent().isEmpty()) {
            amount++;
            remarks.add("텍스트 추가");
        }

        if (review.getAttachedPhotoIds().isEmpty() && !preReview.getAttachedPhotoIds().isEmpty()) {
            amount--;
            remarks.add("사진 삭제");
        } else if (!review.getAttachedPhotoIds().isEmpty() && preReview.getAttachedPhotoIds().isEmpty()) {
            amount++;
            remarks.add("사진 추가");
        }

        return Map.entry(amount , String.join(", ", remarks));
    }

    public Map.Entry<Integer, String> checkAmount(Review review){
        int amount = 0;
        List<String> remarks = new LinkedList<>();

        if (reviewService.hasContent(review)) {
            amount++;
            remarks.add("1자 이상 텍스트 작성");
        }

        if(reviewService.hasPhoto(review)){
            amount++;
            remarks.add("1장 이상 사진 첨부");
        }

        if(amount > 0 && reviewService.isFirstReview(review.getPlaceId())) {
            amount++;
            remarks.add("특정 장소에 첫 리뷰 작성");
        }

        return Map.entry(amount , String.join(", ", remarks));
    }

    public List<Point> getPoints() {
        return pointRepository.findAll();
    }
}
