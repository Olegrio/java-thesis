package com.olegrio.thesis.entity.headlines;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Entity;

@Getter
@Setter
public class HeadlinesSearch {
    private String q;
    private String country;
    private String category;
    private int size;
}
