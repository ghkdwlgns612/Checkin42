package com.checkin.CheckIn.controller;


import com.checkin.CheckIn.domain.ResultResponseDto;
import com.checkin.CheckIn.service.LogService;
import com.checkin.CheckIn.service.dto.LocationDto;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/log")
@AllArgsConstructor
@Slf4j
public class LogController {

    private final LogService logService;

    @GetMapping("/gaepo")
    public ResultResponseDto showCardGeapo() {
        List<LocationDto> result = logService.usingCardGeapo();
        return ResultResponseDto.builder()
                .message("OK")
                .statusCode(HttpStatus.OK.value())
                .data(result)
                .build();
    }

    @GetMapping("/seocho")
    public  ResultResponseDto showCardSeocho() {
        List<LocationDto> result = logService.usingCardSeocho();
        return ResultResponseDto.builder()
                .message("OK")
                .statusCode(HttpStatus.OK.value())
                .data(result)
                .build();
    }

    @GetMapping("/allcard")
    public ResultResponseDto showCardAll() {
        List<LocationDto> result = logService.usingCardAll();
        return ResultResponseDto.builder()
                .message("OK")
                .statusCode(HttpStatus.OK.value())
                .data(result)
                .build();
    }
}