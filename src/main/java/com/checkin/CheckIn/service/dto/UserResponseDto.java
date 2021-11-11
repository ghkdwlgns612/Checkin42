package com.checkin.CheckIn.service.dto;

import lombok.Builder;
import lombok.Getter;
import java.time.LocalDateTime;

@Getter
public class UserResponseDto {

    private String username;
    private String userCursus;
    private Integer cardNumber;
    private LocalDateTime createdAt;

    @Builder
    public UserResponseDto(String username, String userCursus, Integer cardNumber, LocalDateTime createdAt) {
        this.username = username;
        this.userCursus = userCursus;
        this.cardNumber = cardNumber;
        this.createdAt = createdAt;
    }
}