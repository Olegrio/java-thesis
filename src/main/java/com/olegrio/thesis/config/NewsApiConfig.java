package com.olegrio.thesis.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Getter
@Setter
@Configuration
@ConfigurationProperties(prefix = "api.news.config")
public class NewsApiConfig {
    private String baseUrl;
    private String sourceUrl;
    private String headlinesUrl;
    private String everythingUrl;
    private String key;
}
