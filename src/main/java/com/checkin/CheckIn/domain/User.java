package com.checkin.CheckIn.domain;

import lombok.Getter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.LocalDateTime;

@Entity
@Getter
public class User extends BaseTime{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long intraId;

    private String username;

    private Long cardNumber;

    private LocalDateTime checkIn;

    private LocalDateTime checkOut;
}
