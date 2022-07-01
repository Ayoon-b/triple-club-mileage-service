package com.yoonbin.triple.club.mileage.point.domain;

import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;

@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@EntityListeners(AuditingEntityListener.class)
public class Point {
    @Id
    @Column
    @GeneratedValue
    private Integer id;

    @Column(nullable = false)
    private String reviewId;

    @Column(nullable = false)
    private Integer amount;

    @Column(nullable = false)
    private String remarks;

    @Column(nullable = false)
    private String userId;

    @Column(nullable = false)
    private String placeId;

    @Column
    @CreatedDate
    private LocalDateTime createdAt;

}
