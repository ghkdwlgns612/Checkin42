package com.checkin.CheckIn.service.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
public class NumberOfPeopleDto {
    private Integer gaepo;
    private Integer seocho;
    private Integer maxCapGaepo;
    private Integer maxCapSeocho;

    @Builder
    public NumberOfPeopleDto(Integer gaepo, Integer seocho, Integer maxCapGaepo, Integer maxCapSeocho) {
        this.gaepo = gaepo;
        this.seocho = seocho;
        this.maxCapGaepo = maxCapGaepo;
        this.maxCapSeocho = maxCapSeocho;
    }
}