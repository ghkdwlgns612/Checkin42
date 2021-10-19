package com.checkin.CheckIn.controller;

import com.checkin.CheckIn.domain.ResultResponseDto;
import io.swagger.annotations.Api;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@Api
@RestController
@AllArgsConstructor
@Slf4j
public class ClusterController {

    @GetMapping("/cluster")
    public ResultResponseDto numberOfPeopleCluster() {

        return ResultResponseDto.builder()
                .message("OK")
                .statusCode(HttpStatus.OK.value())
                .data(null)
                .build();
    }
}
