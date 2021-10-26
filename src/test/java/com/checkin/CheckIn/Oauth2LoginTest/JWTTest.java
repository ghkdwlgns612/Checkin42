package com.checkin.CheckIn.Oauth2LoginTest;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.SignatureVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.Verification;
import com.checkin.CheckIn.domain.User;
import com.checkin.CheckIn.resource.YAMLSecurityResource;
import com.checkin.CheckIn.utils.JWTUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.Instant;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
public class JWTTest {

    private static Algorithm ALGORITHM = Algorithm.HMAC256("gpark");

    @Autowired
    private YAMLSecurityResource yamlSecurityResource;

    @Autowired
    private JWTUtils jwtUtils;

    public String makeJWT() {
        return jwtUtils.makeJWT(User.builder()
                .username("gpark")
                .build());
    }

    @DisplayName("1. Validate Json Web Token")
    @Test
    public void validateJWT() {
        String token = makeJWT();
        assertDoesNotThrow(() -> jwtUtils.verfiyMyToken(token));
    }

    @DisplayName("2. Validate failure by Expired")
    @Test
    public void ExpiredJWT() throws InterruptedException {
        String token = JWT.create()
                .withClaim("exp", Instant.now().getEpochSecond() + 1)
                .withClaim("name", "gpark")
                .sign(yamlSecurityResource.getAlgorithm())
                ;
        Thread.sleep(2000);
        assertThrows(TokenExpiredException.class, () -> JWT.require(yamlSecurityResource.getAlgorithm()).build().verify(token));
    }

    @DisplayName("3. Validate failure by Different exp")
    @Test
    public void WrongNameJWT() throws InterruptedException {
        String token = makeJWT();
        Thread.sleep(1000);
        String anotherTimeToken = makeJWT();
        String[] tokenSplit = token.split("\\.");
        String[] tokenSplit2 = anotherTimeToken.split("\\.");
        tokenSplit2[1] = tokenSplit2[1];
        String notValidToken = tokenSplit[0] + "." + tokenSplit2[1] + "." + tokenSplit[2];
        assertThrows(SignatureVerificationException.class, () -> jwtUtils.verfiyMyToken(notValidToken));
    }
}
