package com.checkin.CheckIn.service.dto;

import com.checkin.CheckIn.domain.enumeration.Location;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class LocationDto {
    private Location location;
    private String username;
    private Integer cardNumber;
    private LocalDateTime createTime;

    @Builder
    public LocationDto(Location location, String username, Integer cardNumber, LocalDateTime createTime) {
        this.location = location;
        this.username = username;
        this.cardNumber = cardNumber;
        this.createTime = createTime;
    }
}