package com.checkin.CheckIn.Oauth2LoginTest;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.Cookie;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class TokenRecieveCheckTest {

    @LocalServerPort
    int port;

    RestTemplate client = new RestTemplate();

    @DisplayName("Given token url, then Check set-Cookie exists")
    @Test
    public void setCookieCheck() {
        HttpHeaders httpHeaders = new HttpHeaders();
        HttpEntity<String> http = new HttpEntity<>(null, httpHeaders);
        HttpEntity<String> exchange = client.exchange("http://localhost:" + port + "/mock-make-token/gpark", HttpMethod.GET, http, String.class);
        Assertions.assertNotNull(exchange.getHeaders().get(HttpHeaders.SET_COOKIE));
    }
}
