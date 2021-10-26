package com.checkin.CheckIn.controller;

import com.checkin.CheckIn.domain.ResultResponseDto;
import com.checkin.CheckIn.service.ClusterService;
import com.checkin.CheckIn.service.dto.NumberOfPeopleDto;
import io.swagger.annotations.Api;
import io.swagger.v3.oas.annotations.Operation;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@Api
@RestController
@AllArgsConstructor
@Slf4j
public class ClusterController {

    private final ClusterService clusterService;

    @CrossOrigin(origins = "*")
    @GetMapping("/cluster")
    @Operation(summary = "인원 조회", description = "각 클러스터의 현 인원 및 수용가능 인원을 조회합니다.")
    public ResultResponseDto numberOfPeopleCluster() {
        NumberOfPeopleDto result = clusterService.countNumberOfPeopleCluster();
        return ResultResponseDto.builder()
                .message("OK")
                .statusCode(HttpStatus.OK.value())
                .data(result)
                .build();
    }

    @CrossOrigin(origins = "*")
    @PostMapping("/cluster/fix/gaepo")
    @Operation(summary = "개포 최대인원 수정", description = "개포 클러스터의 최대 수용가능 인원을 수정합니다.")
    public Integer fixGaepoMaxPeople(@RequestParam Integer maxGaepo) {
        Integer result = clusterService.setGaepoMax(maxGaepo);
        return result;
    }

    @CrossOrigin(origins = "*")
    @PostMapping("/cluster/fix/seocho")
    @Operation(summary = "서초 최대인원 수정", description = "서초 클러스터의 최대 수용가능 인원을 수정합니다.")
    public Integer fixSeochoMaxPeople(@RequestParam Integer maxSeocho) {
        Integer result = clusterService.setSeochoMax(maxSeocho);
        return result;
    }
}
