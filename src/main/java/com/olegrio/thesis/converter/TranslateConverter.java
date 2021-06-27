package com.olegrio.thesis.converter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.olegrio.thesis.entity.translate.TranslateResponse;
import org.springframework.core.convert.converter.Converter;

import java.io.IOException;

public class TranslateConverter implements Converter<String, TranslateResponse> {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public TranslateResponse convert(String source) {
        try {
            return objectMapper.readValue(source, TranslateResponse.class);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
