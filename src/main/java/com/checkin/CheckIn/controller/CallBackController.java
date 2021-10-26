package com.checkin.CheckIn.controller;

import com.auth0.jwt.JWT;
import com.checkin.CheckIn.domain.ResultResponseDto;
import com.checkin.CheckIn.repository.UserMapper;
import com.checkin.CheckIn.resource.YAMLSecurityResource;
import com.checkin.CheckIn.service.UserService;
import com.checkin.CheckIn.utils.JWTUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.Date;
import java.util.Optional;

@RestController
public class CallBackController {

    private final JWTUtils jwtUtils;
    private final UserMapper userMapper;
    private final YAMLSecurityResource yamlSecurityResource;

    public CallBackController(JWTUtils jwtUtils, UserMapper userMapper, YAMLSecurityResource yamlSecurityResource) {
        this.jwtUtils = jwtUtils;
        this.userMapper = userMapper;
        this.yamlSecurityResource = yamlSecurityResource;
    }

    @CrossOrigin(origins = "*")
    @GetMapping("/mock-make-token/{username}")
    public ResultResponseDto<String> MockMakeToken(@PathVariable String username, HttpServletResponse response) {
        HttpHeaders httpHeaders = new HttpHeaders();
        Cookie cookie = new Cookie("token", jwtUtils.makeJWT(userMapper.findByName(username)));
        cookie.setMaxAge(7 * 24 * 60 * 60);
        cookie.setPath("/");
        response.addCookie(cookie);
        return ResultResponseDto.<String>builder()
                .statusCode(HttpStatus.OK.value())
                .message("cookie Setting")
                .data(username + "'s token use https://www.base64decode.org/")
                .build();
    }

    @CrossOrigin(origins = "*")
    @GetMapping("/mock-check-token/{username}")
    public ResultResponseDto<String> MockValidateToken(@PathVariable String username, HttpServletRequest request) {
        try {
            Cookie[] cookies = request.getCookies();
            Optional<Cookie> token = Arrays.stream(cookies).findFirst().filter((cookie) -> cookie.getName().equals("token"));
            try {
                jwtUtils.verfiyMyToken(token.get().getValue());
            } catch (Exception e) {
                return ResultResponseDto.<String>builder()
                        .statusCode(HttpStatus.UNAUTHORIZED.value())
                        .message("cookie not exist")
                        .data("Where is my cookie")
                        .build();
            }
            return ResultResponseDto.<String>builder()
                    .statusCode(HttpStatus.OK.value())
                    .message(null)
                    .data("You get the real Cookie")
                    .build();
        } catch (Exception e) {
            return ResultResponseDto.<String>builder()
                    .statusCode(HttpStatus.UNAUTHORIZED.value())
                    .message(e.getMessage())
                    .data(username + "'s token different")
                    .build();
        }
    }
}
