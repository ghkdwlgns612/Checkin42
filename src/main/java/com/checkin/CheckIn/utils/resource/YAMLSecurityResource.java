package com.checkin.CheckIn.utils.resource;

import com.auth0.jwt.algorithms.Algorithm;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

@ConfigurationProperties(prefix = "jwt")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class YAMLSecurityResource {
    private String secret;

    public Algorithm getAlgorithm() {
        return Algorithm.HMAC256(secret);
    }
}
