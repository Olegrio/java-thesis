package com.olegrio.thesis.entity.translate;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class TranslateResponseItem {
    private String text;
    private String detectedLanguageCode;
}