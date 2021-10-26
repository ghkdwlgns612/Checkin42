package com.checkin.CheckIn.service.dto;

import lombok.Builder;
import lombok.Getter;
import java.time.LocalDateTime;

@Getter
public class UserResponseDto {

    private String userId;
    private String userCursus;
    private Integer cardNumber;
    private LocalDateTime createdAt;

    @Builder
    public UserResponseDto(String userId, String userCursus, Integer cardNumber, LocalDateTime createdAt) {
        this.userId = userId;
        this.userCursus = userCursus;
        this.cardNumber = cardNumber;
        this.createdAt = createdAt;
    }
}