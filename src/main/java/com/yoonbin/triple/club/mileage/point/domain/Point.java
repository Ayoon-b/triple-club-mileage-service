package com.yoonbin.triple.club.mileage.point.domain;

import com.yoonbin.triple.club.mileage.review.domain.Review;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@EntityListeners(AuditingEntityListener.class)
public class Point {
    @Id
    @Column
    private int id;

    @ManyToOne
    @JoinColumn(name="reviewId")
    private Review reviewId;

    @Column(nullable = false)
    private Integer amount;

    @Column(nullable = false)
    private String remarks;

    @Column(nullable = false)
    private UUID userId;

    @Column(nullable = false)
    private UUID placeId;

    @Column
    @CreatedDate
    private LocalDateTime createdAt;

}
