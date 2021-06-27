package com.olegrio.thesis.entity.translate;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class GetTokenResponse {
    private String iamToken;
    private String expiresAt;
}
