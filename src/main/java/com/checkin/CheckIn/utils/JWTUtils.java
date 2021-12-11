package com.checkin.CheckIn.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.checkin.CheckIn.domain.User;
import com.checkin.CheckIn.utils.resource.JWTSecurityResource;
import org.springframework.stereotype.Component;

import java.time.Instant;

@Component
public class JWTUtils {

    private final JWTSecurityResource JWTSecurityResource;

    private Long EXPIRED_TIME = 60 * 60 * 24L;

    public JWTUtils(JWTSecurityResource JWTSecurityResource) {
        this.JWTSecurityResource = JWTSecurityResource;
    }

    public String makeJWT(User user) {
        return JWT.create()
                .withClaim("exp", Instant.now().getEpochSecond() + EXPIRED_TIME)
                .withClaim("name", user.getUsername())
                .sign(JWTSecurityResource.getAlgorithm())
                ;
    }

    public DecodedJWT verifyJWT(String token) {
        return JWT.require(JWTSecurityResource.getAlgorithm()).build().verify(token);
    }
}
