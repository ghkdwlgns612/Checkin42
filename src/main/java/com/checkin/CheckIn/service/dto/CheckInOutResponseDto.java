package com.checkin.CheckIn.service.dto;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class CheckInOutResponseDto {
    private String username;
    private Integer cardNumber;
    private LocalDateTime checkIn;
    private LocalDateTime checkOut;

    @Builder
    public CheckInOutResponseDto(String username, Integer cardNumber, LocalDateTime checkIn, LocalDateTime checkOut) {
        this.username = username;
        this.cardNumber = cardNumber;
        this.checkIn = checkIn;
        this.checkOut = checkOut;
    }
}
