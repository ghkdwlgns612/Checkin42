package com.checkin.CheckIn.service;

import com.checkin.CheckIn.domain.User;
import com.checkin.CheckIn.repository.UserMapper;
import com.checkin.CheckIn.service.dto.CheckInOutResponseDto;
import javassist.NotFoundException;
import javassist.bytecode.DuplicateMemberException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@AllArgsConstructor
@Slf4j
public class CheckInOutService {

    private final UserMapper userMapper;
    private final UserService userService;

    @Transactional
    public CheckInOutResponseDto checkInService(String username, Integer cardNumber) throws NotFoundException, DuplicateMemberException {
        if (userMapper.findByName(username).isEmpty())
            userService.createUser(cardNumber, username);
        else
            userMapper.updateCheckInByCardNumber(cardNumber, username);//업데이트를 한 개로 묶지말고 체크인, 체크아웃 업데이트로 나누는게 어떨까?
        User user = userMapper.findByName(username).get();
        return CheckInOutResponseDto.builder()
                .username(user.getUsername())
                .cardNumber(user.getCardNumber())
                .checkIn(user.getCheckIn())
                .checkOut(user.getCheckOut())
                .build();
    }

    @Transactional
    public CheckInOutResponseDto checkOutService(String username) {
        userMapper.updateCheckOutByCardNumber(null, username);
        User user = userMapper.findByName(username).get();
        return CheckInOutResponseDto.builder()
                .username(user.getUsername())
                .cardNumber(user.getCardNumber())
                .checkIn(user.getCheckIn())
                .checkOut(user.getCheckOut())
                .build();
    }
}