package com.checkin.CheckIn.utils.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
public class TokenRequestDto {
    private final String grant_type;
    private final String client_id;
    private final String client_secret;
    private final String code;
    private final String redirect_uri;

    @Builder
    public TokenRequestDto(String grant_type, String client_id, String client_secret, String code, String redirect_uri) {
        this.grant_type = grant_type;
        this.client_id = client_id;
        this.client_secret = client_secret;
        this.code = code;
        this.redirect_uri = redirect_uri;
    }
}
