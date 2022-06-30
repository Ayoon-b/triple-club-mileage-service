package com.yoonbin.triple.club.mileage.review.domain;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.util.Arrays;
import java.util.List;

@Converter
public class ListToStringConverter implements AttributeConverter<List, String> {
    @Override
    public String convertToDatabaseColumn(List attribute) {
        return String.join(",",  attribute);
    }

    @Override
    public List<String> convertToEntityAttribute(String s) {
        return Arrays.asList(s.split(","));
    }
}

