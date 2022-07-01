package com.yoonbin.triple.club.mileage.review.service;

import com.yoonbin.triple.club.mileage.point.domain.Point;
import com.yoonbin.triple.club.mileage.point.repository.PointRepository;
import com.yoonbin.triple.club.mileage.review.domain.Review;
import com.yoonbin.triple.club.mileage.review.repository.ReviewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.AbstractMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ReviewService {
    private final ReviewRepository reviewRepository;
    private final PointRepository pointRepository;

    public List<Review> getReviews(){
        return reviewRepository.findAll();
    }

    public Review saveReview(Review review){
        return reviewRepository.save(review);
    }

    public List<Review> getReviewsByPlaceId(String placeId){
        return reviewRepository.findByPlaceIdOrderByCreatedAt(placeId);
    }

    public List<Review> getReviewsByUserId(String userId){
        return reviewRepository.findByUserId(userId);
    }

    public boolean checkContent(Review review){
        boolean checkContent = false;

        if(review.getContent().length() > 0) {
            checkContent = true;
        }
        return checkContent;
    }

    public boolean checkPhoto(Review review){
        boolean checkPhoto = false;

        if(!review.getAttachedPhotoIds().isEmpty() && !review.getAttachedPhotoIds().get(0).isEmpty()){
            checkPhoto = true;
        }
        return checkPhoto;
    }

    public boolean checkPlace(Review review){
        boolean checkPlace = false;

        if(getReviewsByPlaceId(review.getPlaceId()).isEmpty()){
            checkPlace = true;
        }

        return checkPlace;
    }
    public Map.Entry<Integer, String> updateAmount(Review review){
        int amount = 0;
        String remarks ="";
        Review preReview = reviewRepository.findById(review.getReviewId()).get();


        if(review.getContent().isEmpty()){
            if(!preReview.getContent().isEmpty()){
                amount--;
                remarks+="텍스트 삭제";
            }
        } else {
            if(preReview.getContent().isEmpty()){
                amount++;
                remarks+="텍스트 추가";
            }
        }

        if(review.getAttachedPhotoIds().isEmpty() || review.getAttachedPhotoIds().get(0).isEmpty()){
            if(!preReview.getContent().isEmpty()){
                amount--;
                remarks+="사진 삭제";
            }
        } else if (!review.getAttachedPhotoIds().isEmpty() && !review.getAttachedPhotoIds().get(0).isEmpty()){
            if(preReview.getAttachedPhotoIds().isEmpty() || preReview.getAttachedPhotoIds().get(0).isEmpty()) {
                amount++;
                remarks+="사진 추가";
            }
        }
        AbstractMap.Entry<Integer, String> updateAmount =new AbstractMap.SimpleEntry<>(amount , remarks);
        return updateAmount;
    }

    public Map.Entry<Integer, String> checkAmount(Review review){
        int amount = 0;
        String remarks ="";

        if (checkContent(review)) {
            amount++;
            if(remarks.isEmpty()){
                remarks += "1자 이상 텍스트 작성";
            } else {
                remarks += "1자 이상 텍스트 작성";
            }
        }

        if(checkPhoto(review)){
            amount++;
            if(remarks.isEmpty()){
                remarks+="1장 이상 사진 첨부";
            } else {
                remarks+=", 1장 이상 사진 첨부";
            }
        }

        if(amount > 0){
            if (checkPlace(review)){
                amount++;
                remarks+=", 특정 장소에 첫 리뷰 작성";
            }
        }

        AbstractMap.Entry<Integer, String> checkAmount =new AbstractMap.SimpleEntry<>(amount , remarks);
        return checkAmount;
    }


    public List<String> getUserIdsByPlaceId(String placeId){
        List<String> UserIds = getReviewsByPlaceId(placeId).stream().map(Review::getUserId).collect(Collectors.toList());
        return UserIds;
    }

    public boolean visitedCheck(Review review){
        boolean visited = false;
        if(getUserIdsByPlaceId(review.getPlaceId()).contains(review.getUserId())) {
            visited = true;
        }
        return visited;
    }

    @Transactional
    public Review insertReview(Review review){
        AbstractMap.Entry<Integer, String> checkAmount = checkAmount(review);
        if(checkAmount.getKey() > 0) {
            if (!visitedCheck(review)) {
                reviewRepository.save(review);

                Point point = Point.builder()
                        .reviewId(review.getReviewId())
                        .remarks(checkAmount.getValue())
                        .amount(checkAmount.getKey())
                        .placeId(review.getPlaceId())
                        .userId(review.getUserId())
                        .build();
                pointRepository.save(point);

            } else System.out.println("해당 장소에 이미 작성된 리뷰가 존재합니다.");
        }else System.out.println("텍스트와 사진이 작성되지 않아 리뷰로 등록되지 않습니다.");

        return review;
    }

    @Transactional
    public Review updateReview(String reviewId, Review review){
        AbstractMap.Entry<Integer, String> checkAmount = checkAmount(review);
        Review updateReview = reviewRepository.findById(reviewId).
                orElseThrow(() -> new
                IllegalArgumentException("해당 리뷰가 존재하지 않습니다."));

        if(review.getContent().isEmpty() && review.getAttachedPhotoIds().isEmpty()){
            System.out.println("내용이 비어있습니다.");
            return review;
        }

        if(!review.getContent().isEmpty()){
            updateReview.setContent(review.getContent());
        }
        if(!review.getAttachedPhotoIds().isEmpty()){
            updateReview.setAttachedPhotoIds(review.getAttachedPhotoIds());
        }

        Point point = Point.builder()
                .reviewId(updateReview.getReviewId())
                .remarks(checkAmount.getValue())
                .amount(checkAmount.getKey())
                .placeId(updateReview.getPlaceId())
                .userId(updateReview.getUserId())
                .build();
        pointRepository.save(point);

        return review;
    }

    @Transactional
    public void deleteReview(String reviewId) {
        try {
            Review review = reviewRepository.findById(reviewId).get();
            int amount = 0;
            for (Point p : pointRepository.findByPlaceId(review.getPlaceId())) {
                if(p.getUserId().equals(review.getUserId())){
                    amount+=p.getAmount();
                }
            }
            if(amount>0) amount = -(amount);

            Point point = Point.builder()
                    .reviewId(review.getReviewId())
                    .remarks("리뷰 삭제")
                    .amount(amount)
                    .placeId(review.getPlaceId())
                    .userId(review.getUserId())
                    .build();
            pointRepository.save(point);

            reviewRepository.delete(review);

        } catch (NullPointerException e) {
            throw new NullPointerException(e.getMessage());
        }
    }

    public Review getReviewByReviewId(String reviewId) {
        return reviewRepository.findById(reviewId).get();
    }
}

