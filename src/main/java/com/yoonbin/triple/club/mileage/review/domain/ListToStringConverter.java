package com.yoonbin.triple.club.mileage.review.domain;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Converter
public class ListToStringConverter implements AttributeConverter<List, String> {
    @Override
    public String convertToDatabaseColumn(List attribute) {
        return String.join(",",  attribute);
    }

    @Override
    public List<String> convertToEntityAttribute(String str) {
        return Arrays.stream(str.split(",")).filter(s -> !s.isEmpty()).collect(Collectors.toList());
    }
}

