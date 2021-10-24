package com.checkin.CheckIn.service;

import com.checkin.CheckIn.repository.UserMapper;
import com.checkin.CheckIn.service.dto.UserResponseDto;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@AllArgsConstructor
@Slf4j
public class CheckInOutService {

    private final UserMapper userMapper;

    @Transactional
    public UserResponseDto checkInService(Integer cardNumber) {
        return UserResponseDto.builder()
                .cardNumber(cardNumber)
                .userCursus("42Cursus")
                .userId("jihuhwan")
                .userImage("http://jihuhwan.image")
                .createdAt(LocalDateTime.of(2021,10,1,8,0))
                .build();
    }

    @Transactional
    public void checkOutService(Integer cardNumber) {

    }
}