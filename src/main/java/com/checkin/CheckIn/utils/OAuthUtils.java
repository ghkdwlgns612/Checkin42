package com.checkin.CheckIn.utils;

import com.checkin.CheckIn.domain.User;
import com.checkin.CheckIn.repository.usermapper.UserMapper;
import com.checkin.CheckIn.utils.dto.TokenRequestDto;
import com.checkin.CheckIn.utils.dto.TokenResponseDto;
import com.checkin.CheckIn.utils.resource.JWTSecurityResource;
import com.fasterxml.jackson.databind.JsonNode;
import javassist.bytecode.DuplicateMemberException;
import org.springframework.http.*;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class OAuthUtils {

    private final JWTSecurityResource jwtSecurityResource;
    private final ClientRegistrationRepository clientRegistrationRepository;
    private final UserMapper userMapper;
    private final RestTemplate oAuthClient;
    private final ClientRegistration clientRegistration;

    public OAuthUtils(JWTSecurityResource JWTSecurityResource, ClientRegistrationRepository clientRegistrationRepository, UserMapper userMapper) {
        this.jwtSecurityResource = JWTSecurityResource;
        this.clientRegistrationRepository = clientRegistrationRepository;
        this.userMapper = userMapper;
        clientRegistration = clientRegistrationRepository.findByRegistrationId("42");
        oAuthClient = new RestTemplate();
    }

    public void showOauths() {
        clientRegistrationRepository.findByRegistrationId("42");
    }

    private String getValidToken(String code) {
        TokenResponseDto tokens = oAuthClient.postForObject(clientRegistrationRepository.findByRegistrationId("42").getProviderDetails().getTokenUri(),
                TokenRequestDto
                        .builder()
                        .grant_type("authorization_code")
                        .client_id(clientRegistration.getClientId())
                        .client_secret(clientRegistration.getClientSecret())
                        .code(code)
                        .redirect_uri(clientRegistration.getRedirectUri())
                        .build(),
                TokenResponseDto.class
        );
        return tokens.getAccessToken();
    }

    public User OAuthInfoUser(String code) throws DuplicateMemberException {
        ClientRegistration clientRegistration = clientRegistrationRepository.findByRegistrationId("42");
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setBearerAuth(this.getValidToken(code));
        HttpEntity<?> http = new HttpEntity<>(null,httpHeaders);
        ResponseEntity<JsonNode> exchange = oAuthClient.exchange(clientRegistration.getProviderDetails().getUserInfoEndpoint().getUri(), HttpMethod.GET, http, JsonNode.class);
        if (userMapper.findByName(exchange.getBody().get("login").asText()).isPresent())
            return userMapper.findByName(exchange.getBody().get("login").asText()).get();
        JsonNode jsonNode = exchange.getBody().get("cursus_users");
        JsonNode jsonNode1 = jsonNode.get(jsonNode.size() - 1).get("cursus").get("name");
        User user = User.builder().username(exchange.getBody().get("login").asText())
                .intraId(exchange.getBody().get("id").asLong())
                .build();
        userMapper.save(user);
        return user;
    }
}
