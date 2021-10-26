package com.checkin.CheckIn.utils;

import com.auth0.jwt.JWT;
import com.checkin.CheckIn.domain.User;
import com.checkin.CheckIn.utils.resource.YAMLSecurityResource;
import org.springframework.stereotype.Component;

import java.time.Instant;

@Component
public class JWTUtils {

    private final YAMLSecurityResource yamlSecurityResource;

    private Long EXPIRED_TIME = 60 * 60 * 24L;

    public JWTUtils(YAMLSecurityResource yamlSecurityResource) {
        this.yamlSecurityResource = yamlSecurityResource;
    }

    public String makeJWT(User user) {
        return JWT.create()
                .withClaim("exp", Instant.now().getEpochSecond() + EXPIRED_TIME)
                .withClaim("name", user.getUsername())
                .sign(yamlSecurityResource.getAlgorithm())
                ;
    }

    public void verifyJWT(String token) {
        JWT.require(yamlSecurityResource.getAlgorithm()).build().verify(token);
    }
}
