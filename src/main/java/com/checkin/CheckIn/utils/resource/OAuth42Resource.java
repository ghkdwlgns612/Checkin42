package com.checkin.CheckIn.utils.resource;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "42")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OAuth42Resource {

    private String clientId;
    private String clientSecret;
    private String redirectUri;
}
