package com.checkin.CheckIn.controller;

import com.auth0.jwt.JWT;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.checkin.CheckIn.domain.ResultResponseDto;
import com.checkin.CheckIn.repository.UserMapper;
import com.checkin.CheckIn.utils.resource.YAMLSecurityResource;
import com.checkin.CheckIn.utils.JWTUtils;
import io.swagger.annotations.Api;
import io.swagger.v3.oas.annotations.Operation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.Optional;

@Api
@RestController
@Slf4j
@CrossOrigin(origins = {"http://localhost:3000", "http://checkinclone.42cadet.kr/"},
        allowCredentials = "true")
public class CallBackController {

    private final JWTUtils jwtUtils;
    private final UserMapper userMapper;

    public CallBackController(JWTUtils jwtUtils, UserMapper userMapper) {
        this.jwtUtils = jwtUtils;
        this.userMapper = userMapper;
    }

    @GetMapping("/mock-make-token/{username}")
    @Operation(summary = "쿠키 자동 설정", description = "username으로 JWT를 만들고 set-Cookie를 통해서 \"/\" path에 쿠키를 자동 세팅해줍니다.")
    public ResultResponseDto<String> MockMakeToken(@PathVariable String username, HttpServletResponse response, HttpServletRequest request) {
        HttpHeaders httpHeaders = new HttpHeaders();
        if (userMapper.findByName(username).isPresent()) {
            Cookie cookie = new Cookie("basictoken", jwtUtils.makeJWT(userMapper.findByName(username).get()));
            cookie.setMaxAge(7 * 24 * 60 * 60);
            cookie.setPath("/");
            cookie.setDomain("42cadet.kr");
            Cookie cookie1 = new Cookie("localtoken", jwtUtils.makeJWT(userMapper.findByName(username).get()));
            cookie1.setMaxAge(7 * 24 * 60 * 60);
            cookie1.setPath("/");
            cookie1.setDomain("localhost");
            response.addCookie(cookie);
            response.addCookie(cookie1);
            ResponseCookie responseCookie = ResponseCookie.from("httpsNoneToken", jwtUtils.makeJWT(userMapper.findByName(username).get()))
                    .domain("42cadet.kr")
                    .secure(true)
                    .sameSite("None")
                    .maxAge(7 * 24 * 60 * 60)
                    .build();
            ResponseCookie responseCookie1 = ResponseCookie.from("httpsLaxToken", jwtUtils.makeJWT(userMapper.findByName(username).get()))
                    .domain("42cadet.kr")
                    .secure(true)
                    .sameSite("Lax")
                    .maxAge(7 * 24 * 60 * 60)
                    .build();
            ResponseCookie responseCookie2 = ResponseCookie.from("httpsStrictToken", jwtUtils.makeJWT(userMapper.findByName(username).get()))
                    .domain("42cadet.kr")
                    .secure(true)
                    .sameSite("Strict")
                    .maxAge(7 * 24 * 60 * 60)
                    .build();
            response.addHeader("Set-Cookie", responseCookie.toString());
            response.addHeader("Set-Cookie", responseCookie1.toString());
            response.addHeader("Set-Cookie", responseCookie2.toString());
            return ResultResponseDto.<String>builder()
                    .statusCode(HttpStatus.OK.value())
                    .message("4 option cookie Setting")
                    .data(username + "'s token use https://www.base64decode.org/")
                    .build();
        } else {
            return ResultResponseDto.<String>builder()
                    .statusCode(HttpStatus.BAD_REQUEST.value())
                    .message("No such user exists")
                    .data(null)
                    .build();
        }
    }

    @GetMapping("/mock-verfiy-token/{username}")
    @Operation(summary = "쿠키 값 수신 및 검증", description = "username으로 RequestHeader의 쿠키를 통해 전달된 JWT가 정상적인지 검증")
    public ResultResponseDto<String> MockValidateToken(@PathVariable String username, HttpServletRequest request) {
        try {
            Cookie[] cookies = request.getCookies();
            Optional<Cookie> token = Arrays.stream(cookies).findFirst().filter((cookie) -> cookie.getName().equals("token"));
            Cookie cookie;
            try {
                cookie = token.orElseThrow();
                jwtUtils.verifyJWT(cookie.getValue());
            } catch (Exception e) {
                return ResultResponseDto.<String>builder()
                        .statusCode(HttpStatus.UNAUTHORIZED.value())
                        .message("cookie not exist")
                        .data("Where is my cookie")
                        .build();
            }
            DecodedJWT decode = JWT.decode(cookie.getValue());
            String name = decode.getClaim("name").asString();
            if (name.equals(username))
                return ResultResponseDto.<String>builder()
                    .statusCode(HttpStatus.OK.value())
                    .message(null)
                    .data("You get the real Cookie")
                    .build();
            else
                return ResultResponseDto.<String>builder()
                        .statusCode(HttpStatus.OK.value())
                        .message("username not matched")
                        .data(null)
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
