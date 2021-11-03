package com.checkin.CheckIn.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class Log {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;

    private Integer cardNumber;

    private String type;

    private LocalDateTime checkIn;

    private LocalDateTime checkOut;

    @Builder
    public Log(String username, Integer cardNumber, String type, LocalDateTime checkIn, LocalDateTime checkOut) {
        this.username = username;
        this.cardNumber = cardNumber;
        this.type = type;
        this.checkIn = checkIn;
        this.checkOut = checkOut;
    }

}
