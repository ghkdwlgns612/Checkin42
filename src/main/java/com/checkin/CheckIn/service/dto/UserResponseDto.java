package com.checkin.CheckIn.service.dto;

import lombok.Builder;
import lombok.Getter;
import java.time.LocalDateTime;

@Getter
public class UserResponseDto {

    private String username;
    private String userCursus;
    private Integer cardNumber;
    private LocalDateTime checkIn;
    private LocalDateTime checkOut;
    private String userImage;

    @Builder
    public UserResponseDto(String username, String userCursus, Integer cardNumber, LocalDateTime checkIn, LocalDateTime checkOut, String userImage) {
        this.username = username;
        this.userCursus = userCursus;
        this.cardNumber = cardNumber;
        this.checkIn = checkIn;
        this.checkOut = checkOut;
        this.userImage = userImage;
    }
}