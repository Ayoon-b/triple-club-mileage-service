package com.yoonbin.triple.club.mileage.review.domain;

import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@EntityListeners(AuditingEntityListener.class)
public class Review {
    @Id
    @Column
    private String reviewId;

    @Column(nullable = false)
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
