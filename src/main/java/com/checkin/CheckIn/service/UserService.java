package com.checkin.CheckIn.service;

import com.checkin.CheckIn.domain.User;
import com.checkin.CheckIn.repository.UserMapper;
import com.checkin.CheckIn.service.dto.UserResponseDto;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@AllArgsConstructor
@Slf4j
public class UserService {

    private final UserMapper userMapper;

    @Transactional
    public UserResponseDto userInfoService(String username) {
        User result = userMapper.findByName(username);
        return UserResponseDto.builder()
                .userCursus("42 Cursus") //Oauth필요.
                .createdAt(result.getCreated())
                .cardNumber(result.getCardNumber())
                .userId(result.getUsername())
                .build();
    }

    @Transactional
    public List<User> allUserInfo() {
        List<User> users = userMapper.findAll();
        return users;
    }
}
