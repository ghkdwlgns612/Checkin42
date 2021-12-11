package com.checkin.CheckIn.controller;


import com.checkin.CheckIn.domain.Log;
import com.checkin.CheckIn.domain.ResultResponseDto;
import com.checkin.CheckIn.service.LogService;
import com.checkin.CheckIn.service.dto.LocationDto;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

import java.util.List;

@RestController
@AllArgsConstructor
public class LogController {

    private final LogService logService;

    @GetMapping("/log/gaepo")
    public ResultResponseDto gaepoLog(@RequestParam Integer pageNum, @RequestParam Integer pageSize) {
        List<Log> result = logService.gaepoLog(pageNum, pageSize);
        return ResultResponseDto.builder()
                .message("OK")
                .statusCode(HttpStatus.OK.value())
                .data(result)
                .build();
    }

    @GetMapping("/log/seocho")
    public ResultResponseDto seochoLog(@RequestParam Integer pageNum, @RequestParam Integer pageSize) {
        List<Log> result = logService.seochoLog(pageNum, pageSize);
        return ResultResponseDto.builder()
                .message("OK")
                .statusCode(HttpStatus.OK.value())
                .data(result)
                .build();
    }
}
