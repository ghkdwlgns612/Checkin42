package com.checkin.CheckIn.service.dto;

import lombok.Builder;
import lombok.Getter;
import java.time.LocalDateTime;

@Getter
public class UserResponseDto {

    private String userImage;
    private String userId;
    private String userCursus;
    private Long cardNumber;
    private LocalDateTime createdAt;

    @Builder
    public UserResponseDto(String userImage, String userId, String userCursus, Long cardNumber, LocalDateTime createdAt) {
        this.userImage = userImage;
        this.userId = userId;
        this.userCursus = userCursus;
        this.cardNumber = cardNumber;
        this.createdAt = createdAt;
    }
}