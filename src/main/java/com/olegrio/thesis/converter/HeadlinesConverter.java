package com.olegrio.thesis.converter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.olegrio.thesis.entity.headlines.HeadlineResponse;
import org.springframework.core.convert.converter.Converter;

import java.io.IOException;

public class HeadlinesConverter implements Converter<String, HeadlineResponse> {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public HeadlineResponse convert(String source) {
        try {
            return objectMapper.readValue(source, HeadlineResponse.class);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
