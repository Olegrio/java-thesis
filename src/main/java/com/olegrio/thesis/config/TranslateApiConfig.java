package com.olegrio.thesis.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Getter
@Setter
@Configuration
@ConfigurationProperties(prefix = "api.translate.config")
public class TranslateApiConfig {
    private String key;
    private String baseUrl;
    private String folder;
    private String oauthToken;
    private String tokenUrl;
}
