package com.example.portfoliopagebuilder_bnd.common.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.persistence.AttributeConverter;
import java.io.IOException;
import java.util.List;
import java.util.Set;

public class StringSetConverter implements AttributeConverter<Set, String> {
    private static final ObjectMapper mapper = new ObjectMapper()
            .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

    @Override
    public String convertToDatabaseColumn(Set attribute) {
        try {
            return mapper.writeValueAsString(attribute);
        } catch (JsonProcessingException e) {
            throw new IllegalArgumentException();
        }
    }

    @Override
    public Set<String> convertToEntityAttribute(String dbData) {
        try {
            return mapper.readValue(dbData, Set.class);
        } catch (IOException e) {
            throw new IllegalArgumentException();
        }
    }
}
