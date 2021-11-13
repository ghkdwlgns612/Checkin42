package com.checkin.CheckIn.controller;

import com.auth0.jwt.interfaces.DecodedJWT;
import com.checkin.CheckIn.domain.ResultResponseDto;
import com.checkin.CheckIn.domain.User;
import com.checkin.CheckIn.service.UserService;
import com.checkin.CheckIn.service.dto.UserResponseDto;
import com.checkin.CheckIn.utils.JWTUtils;
import io.swagger.annotations.Api;
import io.swagger.v3.oas.annotations.Operation;
import javassist.NotFoundException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.List;
import java.util.NoSuchElementException;

@Api
@RestController
@AllArgsConstructor
@Slf4j
@CrossOrigin(origins = {"http://localhost:3000", "http://checkinclone.42cadet.kr/"},
        allowCredentials = "true")
public class UserController {

    private final UserService userService;
    private final JWTUtils jwtUtils;

    @GetMapping("/status")
    @Operation(summary = "쿠키를 통한 유저 정보 조회")
    public ResultResponseDto<Object> userStatus(HttpServletRequest request) throws NotFoundException {
        Cookie token = Arrays.stream(request.getCookies())
                .filter(cookie -> cookie.getName().equals("token"))
                .findFirst()
                .orElseThrow(() -> new NoSuchElementException("There is no cookie named token in request"));
        DecodedJWT decodedJWT = jwtUtils.verifyJWT(token.getValue());
        UserResponseDto userResponseDto = userService.userInfoService(decodedJWT.getClaim("name").asString());
        return ResultResponseDto.builder()
                .message("OK")
                .statusCode(HttpStatus.OK.value())
                .data(userResponseDto)
                .build();
    }

    @GetMapping("/user")
    @Operation(summary = "유저 정보 조회", description = "유저의 상세 정보를 얻어올 때 사용합니다.")
    public ResultResponseDto<Object> userInfo(@Deprecated @RequestParam String username) throws NotFoundException {
        UserResponseDto result = userService.userInfoService(username);
        return ResultResponseDto.builder()
                .message("OK")
                .statusCode(HttpStatus.OK.value())
                .data(result)
                .build();
    }

    @GetMapping("/user/token")
    @Operation(summary = "유저 정보 조회", description = "유저의 상세 정보를 얻어올 때 사용합니다.")
    public ResultResponseDto<Object> userInfo(@CookieValue(value = "token") Cookie cookie) throws NotFoundException {
        DecodedJWT decodedJWT = jwtUtils.verifyJWT(cookie.getValue());
        UserResponseDto result = userService.userInfoService(decodedJWT.getClaim("name").asString());
        return ResultResponseDto.builder()
                .message("OK")
                .statusCode(HttpStatus.OK.value())
                .data(result)
                .build();
    }

    @PostMapping("/user")
    @Operation(summary = "유저 등록", description = "DB에 유저를 등록합니다.")
    public ResultResponseDto<Object> createUser(@RequestParam String username) throws Exception {
        UserResponseDto result = userService.createUser(username);
        return ResultResponseDto.builder()
                .message("OK")
                .statusCode(HttpStatus.OK.value())
                .data(result)
                .build();
    }

    @PutMapping("/user")
    @Operation(summary = "유저 정보 변경", description = "DB에 유저를 찾아 cardNumber를 변경합니다.")
    public ResultResponseDto<Object> updateUser(@RequestParam(required = false) Integer cardNumber,
                                        @RequestParam String username) throws NotFoundException {
        userService.updateUser(cardNumber, username);
        return ResultResponseDto.builder()
                .message("OK")
                .statusCode(HttpStatus.OK.value())
                .data(null)
                .build();
    }

    @DeleteMapping("/user")
    @Operation(summary = "유저 삭제", description = "DB의 user를 삭제합니다.")
    public ResultResponseDto<Object> deleteUser(@RequestParam String username) throws NotFoundException {
        userService.deleteUser(username);
        return ResultResponseDto.builder()
                .message("OK")
                .statusCode(HttpStatus.OK.value())
                .data(null)
                .build();
    }

    @GetMapping("/user/all")
    @Operation(summary = "모든 유저 조회", description = "DB내의 모든 유저의 정보를 조회합니다.")
    public ResultResponseDto<Object> allUserInfo() {
        List<User> result = userService.allUserInfo();
        return ResultResponseDto.builder()
                .message("OK")
                .statusCode(HttpStatus.OK.value())
                .data(result)
                .build();
    }

}