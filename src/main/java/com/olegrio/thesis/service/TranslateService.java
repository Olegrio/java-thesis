package com.olegrio.thesis.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.olegrio.thesis.config.TranslateApiConfig;
import com.olegrio.thesis.converter.TranslateConverter;
import com.olegrio.thesis.entity.translate.GetTokenResponse;
import com.olegrio.thesis.entity.translate.TranslateResponse;
import okhttp3.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.core.convert.converter.Converter;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.*;

@Component
public class TranslateService {
    private TranslateApiConfig config;
    private ApplicationContext context;
    private String iamToken;

    @Autowired
    public TranslateService(TranslateApiConfig config, ApplicationContext context) {
        this.config = config;
        this.context = context;
        this.initIamToken();
    }


    /**
     * пост запрос для перевода текста
     * @param url
     * @param texts
     * @return
     */
    public String requestPOST(String url, List<String> texts){
        ObjectMapper mapper = new ObjectMapper();
        ObjectNode rootNode = mapper.createObjectNode();
        String jsonString = null;

        try {

            rootNode.put("targetLanguageCode", "ru");
            rootNode.put("folderId", config.getFolder());
            rootNode.set("texts", mapper.convertValue(texts, JsonNode.class));

            jsonString = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(rootNode);
        } catch (JsonProcessingException e) {
            System.out.println("Ошибка создания json");
            e.printStackTrace();
        }

        String result = null;
        OkHttpClient client1 = new OkHttpClient().newBuilder()
                .build();
        RequestBody body = RequestBody.create(jsonString.getBytes(StandardCharsets.UTF_8));
        Request request = new Request.Builder()
                .url(url)
                .method("POST", body)
                .addHeader("Authorization", "Bearer ".concat(this.iamToken))
                .addHeader("Content-Type", "application/json")
                .build();
        try {
            Response response = client1.newCall(request).execute();
            result = Objects.requireNonNull(response.body()).string();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    public TranslateResponse translate(List<String> texts){
        Converter<String, TranslateResponse> translateConverter = new TranslateConverter();
        return translateConverter.convert(requestPOST(config.getBaseUrl(), texts));
    }

    @Scheduled(fixedRate = 18000000)
    public void initIamToken(){
        ObjectMapper mapper = new ObjectMapper();
        ObjectNode rootNode = mapper.createObjectNode();
        String jsonString = null;

        try {
            rootNode.put("yandexPassportOauthToken", config.getOauthToken());
            jsonString = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(rootNode);
        } catch (JsonProcessingException e) {
            System.out.println("Ошибка получения токена");
            e.printStackTrace();
        }

        String result = null;
        OkHttpClient client1 = new OkHttpClient().newBuilder()
                .build();
        RequestBody body = RequestBody.create(jsonString.getBytes(StandardCharsets.UTF_8));
        Request request = new Request.Builder()
                .url(config.getTokenUrl())
                .method("POST", body)
                .addHeader("Content-Type", "application/json")
                .build();
        try {
            Response response = client1.newCall(request).execute();
            result = Objects.requireNonNull(response.body()).string();
            GetTokenResponse tokenResponse = mapper.readValue(result, GetTokenResponse.class);
            this.iamToken = tokenResponse.getIamToken();
            System.out.println("iamToken получен");
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
