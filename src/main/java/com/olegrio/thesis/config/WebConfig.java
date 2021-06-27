package com.olegrio.thesis.config;

import com.olegrio.thesis.converter.HeadlinesConverter;
import com.olegrio.thesis.converter.TranslateConverter;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addFormatters(FormatterRegistry registry) {
        registry.addConverter(new HeadlinesConverter());
        registry.addConverter(new TranslateConverter());
    }
}
