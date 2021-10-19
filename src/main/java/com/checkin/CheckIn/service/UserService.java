package com.checkin.CheckIn.service;

import com.checkin.CheckIn.domain.User;
import com.checkin.CheckIn.repository.UserMapper;
import com.checkin.CheckIn.service.dto.UserResponseDto;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@AllArgsConstructor
@Slf4j
public class UserService {

    private final UserMapper userMapper;

    @Transactional
    public UserResponseDto userInfoService() {
        return UserResponseDto.builder()
                .userCursus("42 Cursus")
                .createdAt(LocalDateTime.of(2021,10,1,8,0))
                .cardNumber(1010)
                .userImage("jihuhwan.image")
                .userId("jihuhwan")
                .build();
    }
    @Transactional
    public List<User> allUserInfo() {
        List<User> users = userMapper.findAll();
        return users;
    }
}
