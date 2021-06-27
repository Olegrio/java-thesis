package com.olegrio.thesis.entity.translate;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@ToString
public class TranslateResponse {
    private List<TranslateResponseItem> translations;
}

