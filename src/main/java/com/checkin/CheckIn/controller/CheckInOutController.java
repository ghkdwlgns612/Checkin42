package com.checkin.CheckIn.controller;

import com.checkin.CheckIn.domain.ResultResponseDto;
import com.checkin.CheckIn.service.CheckInOutService;
import com.checkin.CheckIn.service.dto.UserResponseDto;
import io.swagger.annotations.Api;
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
    public ResultResponseDto checkIn(@RequestParam Integer cardNumber) {
        UserResponseDto result = checkInOutService.checkInService(cardNumber);
        return ResultResponseDto.builder()
                .message("OK")
                .statusCode(HttpStatus.OK.value())
                .data(result)
                .build();
    }

    @PostMapping("/user/checkout")
    public ResultResponseDto checkOut(@RequestParam Integer cardNumber) {
        checkInOutService.checkOutService(cardNumber);
        return ResultResponseDto.builder()
                .message("OK")
                .statusCode(HttpStatus.OK.value())
                .data(null)
                .build();
    }
}
