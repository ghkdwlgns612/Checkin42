package com.checkin.CheckIn.service;

import com.checkin.CheckIn.domain.User;
import com.checkin.CheckIn.repository.usermapper.UserMapper;
import com.checkin.CheckIn.service.dto.UserResponseDto;
import javassist.NotFoundException;
import javassist.bytecode.DuplicateMemberException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
@Slf4j
public class UserService {

    private final UserMapper userMapper;

    @Transactional
    public UserResponseDto userInfoService(String username) throws NotFoundException {
        Optional<User> result = userMapper.findByName(username);
        if (result.isEmpty())
            throw new NotFoundException("존재하지 않는 회원입니다.");
        return UserResponseDto.builder()
                .userCursus("42 Cursus") //Oauth필요.
                .checkIn(result.get().getCheckIn())
                .checkOut(result.get().getCheckOut())
                .cardNumber(result.get().getCardNumber())
                .username(result.get().getUsername())
                .build();
    }

    @Transactional
    public List<User> allUserInfo() {
        return userMapper.findAll();
    }

    @Transactional
    public UserResponseDto createUser(String username) throws DuplicateMemberException {
        User user = User.builder()
                .username(username)
                .checkIn(LocalDateTime.now())
                .cardNumber(null)
                .intraId(86212L)
                .build();
        if (userMapper.findByName(username).isPresent())
            throw new DuplicateMemberException("이미 존재하는 회원입니다.");
        userMapper.save(user);
        return UserResponseDto.builder()
                .username(user.getUsername())
                .cardNumber(user.getCardNumber())
                .userCursus("42 Cursus")
                .build();
    }

    @Transactional
    public UserResponseDto createUser(Integer cardNumber, String username) throws DuplicateMemberException {
        User user = User.builder()
                .username(username)
                .checkIn(LocalDateTime.now())
                .cardNumber(cardNumber)
                .intraId(86212L)
                .build();
        if (userMapper.findByName(username).isPresent())
            throw new DuplicateMemberException("이미 존재하는 회원입니다.");
        userMapper.save(user);
        return UserResponseDto.builder()
                .username(user.getUsername())
                .cardNumber(user.getCardNumber())
                .userCursus("42 Cursus")
                .build();
    }

    @Transactional
    public void updateUser(Integer cardNumber, String username) throws NotFoundException {
        userInfoService(username);
        userMapper.updateByCardNumber(cardNumber, username);
    }

    @Transactional
    public void deleteUser(String username) throws NotFoundException {
        userInfoService(username);
        userMapper.deleteByName(username);
    }
}
