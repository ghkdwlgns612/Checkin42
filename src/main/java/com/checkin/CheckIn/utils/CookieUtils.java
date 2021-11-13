package com.checkin.CheckIn.utils;

import com.checkin.CheckIn.domain.User;
import org.springframework.http.ResponseCookie;
import org.springframework.stereotype.Component;

import javax.servlet.http.Cookie;
import java.util.ArrayList;
import java.util.List;

@Component
public class CookieUtils {

    private final JWTUtils jwtUtils;

    public CookieUtils(JWTUtils jwtUtils) {
        this.jwtUtils = jwtUtils;
    }

    public ResponseCookie[] makeJWTCookie(User user) {
        ResponseCookie responseCookie = ResponseCookie.from("token", jwtUtils.makeJWT(user))
                .domain("42cadet.kr")
                .sameSite("Lax")
                .path("/")
                .httpOnly(true)
                .maxAge(7 * 24 * 60 * 60)
                .build();
        return new ResponseCookie[]{responseCookie};
    }

    @Deprecated
    public ResponseCookie[] makeAllConditionCookie(User user) {
        ResponseCookie responseCookie = ResponseCookie.from("httpsNoneToken", jwtUtils.makeJWT(user))
                .domain("42cadet.kr")
                .path("/")
                .secure(true)
                .sameSite("None")
                .maxAge(7 * 24 * 60 * 60)
                .build();
        ResponseCookie responseCookie1 = ResponseCookie.from("httpsLaxToken", jwtUtils.makeJWT(user))
                .domain("42cadet.kr")
                .secure(true)
                .sameSite("Lax")
                .maxAge(7 * 24 * 60 * 60)
                .build();
        ResponseCookie responseCookie2 = ResponseCookie.from("httpsStrictToken", jwtUtils.makeJWT(user))
                .domain("42cadet.kr")
                .secure(true)
                .sameSite("Strict")
                .maxAge(7 * 24 * 60 * 60)
                .build();
        ResponseCookie responseCookie3 = ResponseCookie.from("basictoken", jwtUtils.makeJWT(user))
                .domain("42cadet.kr")
                .path("/")
                .maxAge(7 * 24 * 60 * 60)
                .build();
        ResponseCookie responseCookie4 = ResponseCookie.from("httpOnlyToken", jwtUtils.makeJWT(user))
                .domain("42cadet.kr")
                .path("/")
                .maxAge(7 * 24 * 60 * 60)
                .httpOnly(true)
                .build();
        ResponseCookie responseCookie5 = ResponseCookie.from("localtoken", jwtUtils.makeJWT(user))
                .domain("localhost")
                .path("/")
                .maxAge(7 * 24 * 60 * 60)
                .build();
        ResponseCookie responseCookie6 = ResponseCookie.from("localhttpOnlytoken", jwtUtils.makeJWT(user))
                .domain("localhost")
                .path("/")
                .maxAge(7 * 24 * 60 * 60)
                .httpOnly(true)
                .build();
        return new ResponseCookie[]{responseCookie, responseCookie1, responseCookie2,
                responseCookie3, responseCookie4, responseCookie5, responseCookie6};
    }
}
