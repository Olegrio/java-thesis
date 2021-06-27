package com.olegrio.thesis.service;

import com.olegrio.thesis.config.NewsApiConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

@Component
public class NewsService {
    private NewsApiConfig config;
    private ApplicationContext context;

    @Autowired
    public NewsService(NewsApiConfig config, ApplicationContext context) {
        this.config = config;
        this.context = context;
    }

    private String getBaseQueryString(String url){
        return url.concat("?apiKey=").concat(config.getKey());
    }

    public String requestGET(String url){
        HttpClient client = HttpClient.newHttpClient();

        HttpRequest getRequest = HttpRequest.newBuilder(URI.create(url))
                .GET()
                .header("accept", "application/json")
                .build();

        HttpResponse<String> response = null;
        try {
            response = client.send(getRequest, HttpResponse.BodyHandlers.ofString());
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
        return response != null ? response.body() : null;
    }

    public String getSources(){
        return requestGET(getBaseQueryString(config.getSourceUrl()));
    }

    public String getHeadlines(String query, String country, String category, int size){
        String baseUrl = getBaseQueryString(config.getHeadlinesUrl());
        String fullUrl = baseUrl + "&q=" +query + "&country=" + country + "&category=" + category + "&pageSize=" + size;
        return requestGET(fullUrl);
    }
}
