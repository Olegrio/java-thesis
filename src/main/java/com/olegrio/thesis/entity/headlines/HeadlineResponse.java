package com.olegrio.thesis.entity.headlines;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@ToString
public class HeadlineResponse {
    private String status;
    private int totalResults;
    private List<HeadlineArticle> articles;
}
