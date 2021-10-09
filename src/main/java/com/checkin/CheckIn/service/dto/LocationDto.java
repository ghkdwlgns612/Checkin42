package com.checkin.CheckIn.service.dto;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class LocationDto {
    private String location;
    private String username;
    private Long cardNumber;
    private LocalDateTime createTime;

    @Builder
    public LocationDto(String location, String username, Long cardNumber, LocalDateTime createTime) {
        this.location = location;
        this.username = username;
        this.cardNumber = cardNumber;
        this.createTime = createTime;
    }
}