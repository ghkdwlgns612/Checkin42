package com.checkin.CheckIn.domain;

import com.checkin.CheckIn.domain.common.BaseTime;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.LocalDateTime;

//@Entity //JPA사용 시 활성화
@Getter
@NoArgsConstructor
public class User extends BaseTime {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long intraId;

    private String username;

    private Integer cardNumber;

    private LocalDateTime checkIn;

    private LocalDateTime checkOut;

    @Builder
    public User(Long intraId, String username, Integer cardNumber, LocalDateTime checkIn, LocalDateTime checkOut) {
        this.intraId = intraId;
        this.username = username;
        this.cardNumber = cardNumber;
        this.checkIn = checkIn;
        this.checkOut = checkOut;
    }

    //JDBC 사용 시 SAVE 후 객체업데이트 필요.
    public void setUserKey(Number key) {
        this.id = (Long) key;
    }
}

