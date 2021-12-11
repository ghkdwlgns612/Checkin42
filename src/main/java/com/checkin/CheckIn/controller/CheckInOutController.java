package com.checkin.CheckIn.controller;

import com.auth0.jwt.interfaces.DecodedJWT;
import com.checkin.CheckIn.domain.ResultResponseDto;
import com.checkin.CheckIn.service.CheckInOutService;
import com.checkin.CheckIn.service.dto.CheckInOutResponseDto;
import com.checkin.CheckIn.service.dto.UserResponseDto;
import com.checkin.CheckIn.utils.JWTUtils;
import io.swagger.annotations.Api;
import io.swagger.v3.oas.annotations.Operation;
import javassist.NotFoundException;
import javassist.bytecode.DuplicateMemberException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;

@Api
@RestController
@AllArgsConstructor
@Slf4j
@CrossOrigin(origins = {"http://localhost:3000", "http://checkinclone.42cadet.kr/"},
        allowCredentials = "true")
public class CheckInOutController {

    private final CheckInOutService checkInOutService;
    private final JWTUtils jwtUtils;

    @PostMapping("/user/checkin")
    @Operation(summary = "체크인", description = "체크인 버튼을 클릭 할 경우 카드 정보를 저장합니다.")
    public ResultResponseDto checkIn(@RequestParam Integer cardNumber,
                                     @CookieValue(value = "token") Cookie cookie) throws DuplicateMemberException, NotFoundException {
        DecodedJWT decodedJWT = jwtUtils.verifyJWT(cookie.getValue());
        CheckInOutResponseDto result = checkInOutService.checkInService(decodedJWT.getClaim("name").asString(), cardNumber);
        return ResultResponseDto.builder()
                .message("OK")
                .statusCode(HttpStatus.OK.value())
                .data(result)
                .build();
    }

    @PostMapping("/user/checkout")
    @Operation(summary = "체크아웃", description = "체크아웃 할 경우 카드 정보를 null로 변경합니다.")
    public ResultResponseDto checkOut(@CookieValue(value = "token") Cookie cookie) {
        DecodedJWT decodedJWT = jwtUtils.verifyJWT(cookie.getValue());
        CheckInOutResponseDto result = checkInOutService.checkOutService(decodedJWT.getClaim("name").asString());
        return ResultResponseDto.builder()
                .message("OK")
                .statusCode(HttpStatus.OK.value())
                .data(result)
                .build();
    }
}
