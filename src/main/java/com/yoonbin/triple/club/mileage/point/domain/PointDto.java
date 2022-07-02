package com.yoonbin.triple.club.mileage.point.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@AllArgsConstructor
@Builder
@Data
public class PointDto {
    private String type;
    private String action;
    @JsonProperty("review_id")
    private String reviewId;
    private String content;
    @JsonProperty("attached_photo_ids")
    private List<String> attachedPhotoIds;
    @JsonProperty("user_id")
    private String userId;
    @JsonProperty("place_id")
    private String placeId;
}