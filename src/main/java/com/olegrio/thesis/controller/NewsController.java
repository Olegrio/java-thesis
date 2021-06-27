package com.olegrio.thesis.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.olegrio.thesis.converter.HeadlinesConverter;
import com.olegrio.thesis.entity.headlines.*;
import com.olegrio.thesis.entity.translate.TranslateResponse;
import com.olegrio.thesis.entity.translate.TranslateResponseItem;
import com.olegrio.thesis.service.NewsService;
import com.olegrio.thesis.service.TranslateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


@RestController
@RequestMapping("/news")
public class NewsController {
    private NewsService newsService;
    private TranslateService translateService;


    @Autowired
    public NewsController(NewsService newsService, TranslateService translateService){
        this.newsService = newsService;
        this.translateService = translateService;
    }

    @GetMapping("/sources")
    public String getSources(){
        System.out.println("источники получены");

        return newsService.getSources();
    }

    @CrossOrigin(origins = {"http://localhost:3000", "https://translate.api.cloud.yandex.net"})
    @PostMapping("/headlines")
    public String searchHeadlines(@RequestBody HeadlinesSearch headlinesSearch){
        System.out.println("заголовки получены");
        String response = newsService.getHeadlines(headlinesSearch.getQ(), headlinesSearch.getCountry(), headlinesSearch.getCategory(),10);

        if (!headlinesSearch.getCountry().equalsIgnoreCase("ru")) {

            Converter<String, HeadlineResponse> headlineConverter = new HeadlinesConverter();
            HeadlineResponse headlineResponse = headlineConverter.convert(response);

            TranslateResponse translateResponse = translateService.translate(getTextForTranslation(headlineResponse));
            List<TranslateResponseItem> translations = translateResponse.getTranslations();

            for (int j = 0, step = 0; j < headlineResponse.getArticles().size(); j++, step=+2) {
                headlineResponse.getArticles().get(j).setDescription(translations.get(step).getText());
                headlineResponse.getArticles().get(j).setTitle(translations.get(step+1).getText());
            }

            try {
                ObjectMapper mapper = new ObjectMapper();
                Object json = mapper.readValue(mapper.writeValueAsString(headlineResponse), HeadlineResponse.class);
                response = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(json);
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
        return response;
    }

    /**
     * Формирование списка строк для перевода
     * @param headlineResponse
     * @return
     */
    public List<String> getTextForTranslation(HeadlineResponse headlineResponse){
        List<String> texts = new ArrayList<>();

        for (HeadlineArticle article : headlineResponse.getArticles()) {
            texts.add(article.getDescription());
            texts.add(article.getTitle());
        }
        return texts;
    }
}
