package com.checkin.CheckIn.service;

import com.checkin.CheckIn.domain.User;
import com.checkin.CheckIn.domain.enumeration.Location;
import com.checkin.CheckIn.repository.UserMapper;
import com.checkin.CheckIn.service.dto.LocationDto;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
@Transactional
public class LogService {

//    private final UserRepository userRepository; //JPA또는 JDBC사용 시 활성화
//
//    @Autowired
//    public LogService(UserRepository userRepository) {
//        this.userRepository = userRepository;
//    }

    private final UserMapper userMapper;

    public LogService(UserMapper userMapper) {
        this.userMapper = userMapper;
    }

    public List<LocationDto> usingCardGeapo() {
        //List<User> users = userRepository.findByCardNumberGeapo();
        List<User> users = userMapper.findByCardNumberGeapo();
        List<LocationDto> result = new ArrayList<>();
        users.stream().forEach(user -> {
            LocationDto locationDto = LocationDto.builder()
                    .cardNumber(user.getCardNumber())
                    .createTime(user.getCreated())
                    .location(Location.GAEPO)
                    .username(user.getUsername())
                    .build();
            result.add(locationDto);
        });
        return result;
    }

    public List<LocationDto> usingCardSeocho() {
        //List<User> users = userRepository.findByCardNumberSeocho();
        List<User> users = userMapper.findByCardNumberSeocho();
        List<LocationDto> result = new ArrayList<>();
        users.stream().forEach(user -> {
            LocationDto locationDto = LocationDto.builder()
                    .cardNumber(user.getCardNumber())
                    .createTime(user.getCreated())
                    .location(Location.SEOCHO)
                    .username(user.getUsername())
                    .build();
            result.add(locationDto);
        });
        return result;
    }

    public List<LocationDto> usingCardAll() {
//        List<User> users = userRepository.findAll();
        List<User> users = userMapper.findAll();
        List<LocationDto> result = new ArrayList<>();
        users.stream().forEach(user -> {
            if (user.getCardNumber() < 1000) {
                LocationDto locationDto = LocationDto.builder()
                        .cardNumber(user.getCardNumber())
                        .createTime(user.getCreated())
                        .location(Location.GAEPO)
                        .username(user.getUsername())
                        .build();
                result.add(locationDto);
            } else {
                LocationDto locationDto = LocationDto.builder()
                        .cardNumber(user.getCardNumber())
                        .createTime(user.getCreated())
                        .location(Location.SEOCHO)
                        .username(user.getUsername())
                        .build();
                result.add(locationDto);
            }
        });
        return result;
    }
}