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
        Integer seocho = userMapper.countSeochoPeople();
        Integer gaepo = userMapper.countGaepoPeople();
        return NumberOfPeopleDto.builder()
                .maxCapGaepo(maxGaepo)
                .maxCapSeocho(maxSeocho)
                .gaepo(gaepo)
                .seocho(seocho)
                .build();
    }

    public Integer setGaepoMax(Integer maxGaepo) {
        this.maxGaepo = maxGaepo;
        return maxGaepo;
    }

    public Integer setSeochoMax(Integer maxSeocho) {
        this.maxSeocho = maxSeocho;
        return maxSeocho;
    }
}