package com.yoonbin.triple.club.mileage.review.domain;

public interface AttributeConverter <X, Y> {

    Y convertToDatabaseColumn(X var1);

    X convertToEntityAttribute(Y var1);
}
