package com.checkin.CheckIn.service;

import com.checkin.CheckIn.repository.UserMapper;
import com.checkin.CheckIn.service.dto.NumberOfPeopleDto;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
@Slf4j
public class ClusterService {
    private final UserMapper userMapper;
    private static int maxGaepo = 142;
    private static int maxSeocho = 142;

    public NumberOfPeopleDto countNumberOfPeopleCluster() {
        return NumberOfPeopleDto.builder()
                .maxCapGaepo(maxGaepo)
                .maxCapSeocho(maxSeocho)
                .gaepo(100)
                .seocho(50)
                .build();
    }
}