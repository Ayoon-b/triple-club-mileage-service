package com.yoonbin.triple.club.mileage.review.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(indexes = {
        @Index(name = "idxPlaceId", columnList = "placeId"),
        @Index(name = "idxPlaceIdAndUserId", columnList = "placeId,userId")
        })
public class Review {
    @Id
    @Column
    private String reviewId;

    @Column
    private String content;

    @Column
    @Convert(converter = ListToStringConverter.class)
    private List<String> attachedPhotoIds;

    @Column(nullable = false)
    private String userId;

    @Column(nullable = false)
    private String placeId;

    @Column
    @CreatedDate
    private LocalDateTime createdAt;

    @Column
    @LastModifiedDate
    private LocalDateTime updatedAt;

    @PrePersist
    public void prePersist() {
        if (reviewId == null) {
            reviewId = UUID.randomUUID().toString();
        }
    }
}
