package com.yoonbin.triple.club.mileage.review.service;

import com.yoonbin.triple.club.mileage.point.domain.Point;
import com.yoonbin.triple.club.mileage.point.repository.PointRepository;
import com.yoonbin.triple.club.mileage.review.domain.Review;
import com.yoonbin.triple.club.mileage.review.domain.ReviewDto;
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

    public List<Review> getReviewsByPlaceId(String placeId){
        return reviewRepository.findByPlaceIdOrderByCreatedAt(placeId);
    }

    public List<Review> getReviewsByUserId(String userId){
        return reviewRepository.findByUserId(userId);
    }

    public Map.Entry<Integer, String> checkPhoto(Review review){
        int amount = 0;
        String remarks ="";

        if(!review.getAttachedPhotoIds().get(0).isEmpty()){
            amount++;
            if(remarks.isEmpty()){
                remarks+="1장 이상 사진 첨부";
            }else {
                remarks+=", 1장 이상 사진 첨부";
            }
        }
        AbstractMap.Entry<Integer, String> checkPhoto =new AbstractMap.SimpleEntry<>(amount , remarks);
        return checkPhoto;
    }

    public Map.Entry<Integer, String> checkContent(Review review){
        int amount = 0;
        String remarks ="";

        if(review.getContent().length() > 0) {
            amount++;
            if (remarks.isEmpty()) {
                remarks += "1자 이상 텍스트 작성";
            } else {
                remarks += "1자 이상 텍스트 작성";
            }
        }
        AbstractMap.Entry<Integer, String> checkContent =new AbstractMap.SimpleEntry<>(amount , remarks);
        return checkContent;
    }

    public Map.Entry<Integer, String> checkPlace(Review review){
        int amount = 0;
        String remarks ="";

        if(getReviewsByPlaceId(review.getPlaceId()).isEmpty()){
            amount++;
            if(remarks.isEmpty()){
                remarks+="특정 장소에 첫 리뷰 작성";
            }else {
                remarks+=", 특정 장소에 첫 리뷰 작성";
            }
        }
        AbstractMap.Entry<Integer, String> checkPlace =new AbstractMap.SimpleEntry<>(amount , remarks);
        return checkPlace;
    }

    public Map.Entry<Integer, String> checkAmount(Review review){
        int amount = 0;
        String remarks ="";

        amount += (checkContent(review).getKey() + checkPhoto(review).getKey());
        remarks += (checkContent(review).getValue() + checkPhoto(review).getKey());

        if(amount > 0){
            amount += checkPlace(review).getKey();
            remarks += checkPlace(review).getValue();
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
    public ReviewDto insertReview(Review review){
        AbstractMap.Entry<Integer, String> checkAmount = checkAmount(review);
        ReviewDto reviewDto = null;
        if(checkAmount.getKey() > 0) {
            if (!visitedCheck(review)) {
                Review saveReview = reviewRepository.save(review);

                Point point = Point.builder()
                        .reviewId(review.getReviewId())
                        .remarks(checkAmount.getValue())
                        .amount(checkAmount.getKey())
                        .placeId(review.getPlaceId())
                        .userId(review.getUserId())
                        .build();
                pointRepository.save(point);

                reviewDto = ReviewDto.builder()
                        .type("REVIEW")
                        .action("ADD")
                        .reviewId(saveReview.getReviewId())
                        .content(saveReview.getContent())
                        .attachedPhotoIds(saveReview.getAttachedPhotoIds())
                        .placeId(saveReview.getPlaceId())
                        .userId(saveReview.getUserId())
                        .build();

            } else System.out.println("해당 장소에 이미 작성된 리뷰가 존재합니다.");
        }else System.out.println("텍스트와 사진이 작성되지 않아 리뷰로 등록되지 않습니다.");

        return reviewDto;
    }
}

