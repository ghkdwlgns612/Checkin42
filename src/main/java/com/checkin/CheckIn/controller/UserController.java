package com.checkin.CheckIn.controller;

import com.checkin.CheckIn.domain.ResultResponseDto;
import com.checkin.CheckIn.service.UserService;
import com.checkin.CheckIn.service.dto.UserResponseDto;
import io.swagger.annotations.Api;
import io.swagger.v3.oas.annotations.Operation;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@Api
@RestController
@AllArgsConstructor
@Slf4j
public class UserController {

    private final UserService userService;

    @GetMapping("/user")
    @Operation(summary = "유저 정보 조회", description = "유저의 상세 정보를 얻어올 때 사용합니다.")
    public ResultResponseDto userInfo() {
        UserResponseDto result = userService.userInfoService();
        return ResultResponseDto.builder()
                .message("OK")
                .statusCode(HttpStatus.OK.value())
                .data(result)
                .build();
    }
}