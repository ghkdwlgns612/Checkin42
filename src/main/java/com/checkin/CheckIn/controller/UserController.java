package com.checkin.CheckIn.controller;

import com.checkin.CheckIn.domain.ResultResponseDto;
import com.checkin.CheckIn.domain.User;
import com.checkin.CheckIn.service.UserService;
import com.checkin.CheckIn.service.dto.UserResponseDto;
import io.swagger.annotations.Api;
import io.swagger.v3.oas.annotations.Operation;
import javassist.NotFoundException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api
@RestController
@AllArgsConstructor
@Slf4j
@CrossOrigin(origins = {"http://localhost:3000", "http://checkinclone.42cadet.kr/"},
        allowCredentials = "true")
public class UserController {

    private final UserService userService;

    @GetMapping("/user")
    @Operation(summary = "유저 정보 조회", description = "유저의 상세 정보를 얻어올 때 사용합니다.")
    public ResultResponseDto userInfo(@Deprecated @RequestParam String username) throws NotFoundException {
        UserResponseDto result = userService.userInfoService(username);
        return ResultResponseDto.builder()
                .message("OK")
                .statusCode(HttpStatus.OK.value())
                .data(result)
                .build();
    }

    @PostMapping("/user")
    @Operation(summary = "유저 등록", description = "DB에 유저를 등록합니다.")
    public ResultResponseDto createUser(@RequestParam String username) throws Exception {
        UserResponseDto result = userService.createUser(username);
        return ResultResponseDto.builder()
                .message("OK")
                .statusCode(HttpStatus.OK.value())
                .data(result)
                .build();
    }

    @PutMapping("/user")
    @Operation(summary = "유저 정보 변경", description = "DB에 유저를 찾아 cardNumber를 변경합니다.")
    public ResultResponseDto updateUser(@RequestParam(required = false) Integer cardNumber,
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
    public ResultResponseDto deleteUser(@RequestParam String username) throws NotFoundException {
        userService.deleteUser(username);
        return ResultResponseDto.builder()
                .message("OK")
                .statusCode(HttpStatus.OK.value())
                .data(null)
                .build();
    }

    @GetMapping("/user/all")
    @Operation(summary = "모든 유저 조회", description = "DB내의 모든 유저의 정보를 조회합니다.")
    public ResultResponseDto allUserInfo() {
        List<User> result = userService.allUserInfo();
        return ResultResponseDto.builder()
                .message("OK")
                .statusCode(HttpStatus.OK.value())
                .data(result)
                .build();
    }

}