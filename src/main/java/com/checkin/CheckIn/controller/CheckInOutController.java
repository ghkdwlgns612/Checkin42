package com.checkin.CheckIn.controller;

import com.checkin.CheckIn.domain.ResultResponseDto;
import com.checkin.CheckIn.service.CheckInOutService;
import com.checkin.CheckIn.service.dto.UserResponseDto;
import io.swagger.annotations.Api;
import io.swagger.v3.oas.annotations.Operation;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Api
@RestController
@AllArgsConstructor
@Slf4j
public class CheckInOutController {

    private final CheckInOutService checkInOutService;

    @PostMapping("/user/checkin")
    @Operation(summary = "체크인", description = "체크인 버튼을 클릭 할 경우 카드 정보를 저장합니다.")
    public ResultResponseDto checkIn(@RequestParam Long cardNumber) {
        UserResponseDto result = checkInOutService.checkInService(cardNumber);
        return ResultResponseDto.builder()
                .message("OK")
                .statusCode(HttpStatus.OK.value())
                .data(result)
                .build();
    }

    @PostMapping("/user/checkout")
    @Operation(summary = "체크아웃", description = "체크아웃 할 경우 카드 정보를 null로 변경합니다.")
    public ResultResponseDto checkOut(@RequestParam Long cardNumber) {
        checkInOutService.checkOutService(cardNumber);
        return ResultResponseDto.builder()
                .message("OK")
                .statusCode(HttpStatus.OK.value())
                .data(null)
                .build();
    }
}
