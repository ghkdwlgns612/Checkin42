package com.checkin.CheckIn;

import com.checkin.CheckIn.domain.enumeration.Location;
import com.checkin.CheckIn.domain.User;
import com.checkin.CheckIn.repository.UserMapper;
import com.checkin.CheckIn.repository.emergency.UserRepository;
import com.checkin.CheckIn.service.LogService;
import com.checkin.CheckIn.service.dto.LocationDto;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.annotation.After;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@Slf4j
public class LogTest {

    //    @Autowired
//    private UserRepository userRepository;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private LogService logService;

    List<User> users = new ArrayList<>();

    @BeforeAll
    public void setup() {
        addUsers();
    }

    @AfterAll
    public void clear() { removeUsers(); }

    private void removeUsers() {
        userMapper.deleteTestAll();
    }

    private void addUsers() {
        users.add(User.builder()
                .username("jihuhwan")
                .cardNumber(99)
                .checkIn(LocalDateTime.now().plusDays(1).withHour(6).withMinute(0).withSecond(0))
                .checkOut(null)
                .intraId(86000L)
                .build());
        users.add(User.builder()
                .username("gpark")
                .cardNumber(1099)
                .checkIn(LocalDateTime.now().plusDays(1).withHour(6).withMinute(0).withSecond(0))
                .checkOut(LocalDateTime.now().plusDays(1).withHour(11).withMinute(0).withSecond(0))
                .intraId(86021L)
                .build());
        users.add(User.builder()
                .username("jihuhwan1")
                .cardNumber(1089)
                .checkIn(LocalDateTime.now().plusDays(1).withHour(8).withMinute(0).withSecond(0))
                .checkOut(LocalDateTime.now().plusDays(1).withHour(12).withMinute(0).withSecond(0))
                .intraId(86031L)
                .build());
        users.add(User.builder()
                .username("gpark1")
                .cardNumber(98)
                .checkIn(LocalDateTime.now().plusDays(1).withHour(8).withMinute(0).withSecond(0))
                .checkOut(null)
                .intraId(86041L)
                .build());
        users.add(User.builder()
                .username("hwang")
                .cardNumber(1100)
                .checkIn(LocalDateTime.now().plusDays(1).withHour(8).withMinute(0).withSecond(0))
                .checkOut(null)
                .intraId(86051L)
                .build());

        users.stream().forEach(user -> {
//            userRepository.save(user);
            userMapper.save(user);
        });
    }

    @Test
    @DisplayName("DB에 사용자들이 있을 때 레포지토리에서 모든 회원을 조회 후 users와 사이즈가 같은지 확인")
    public void findAllfromRepository() {
//        List<User> result = userRepository.findAll();
        List<User> result = userMapper.findAll();
        Integer usersNumber = users.size();

        Assertions.assertEquals(result.size(), usersNumber);
    }

    @Test
    @DisplayName("DB에 사용자들이 있을 때 LogService에서 모든 회원 조회 후 서초가 확실히 맞는지 사이즈로 확인")
    public void findAllBySeoCho() {
        List<LocationDto> result = logService.usingCardAll();

        List<User> res1 = users.stream().filter(u -> u.getCardNumber() >= 1000).collect(Collectors.toList());
        List<LocationDto> res2 = result.stream().filter(u -> u.getLocation().equals(Location.SEOCHO)).collect(Collectors.toList());

        Assertions.assertEquals(res1.size(), res2.size());
    }

    @Test
    @DisplayName("DB에 사용자들이 있을 때 서초카드 사용 인원을 찾아 users의 서초 인원과 비교")
    public void findCardSeocho() {
        List<LocationDto> dtos = logService.usingCardSeocho();

        List<User> res1 = users.stream().filter(u -> u.getCardNumber() >= 1000).collect(Collectors.toList());
        List<LocationDto> res2 = dtos.stream().filter(u -> u.getLocation().equals(Location.SEOCHO)).collect(Collectors.toList());

        Assertions.assertEquals(res1.size(), res2.size());
    }

    @Test
    @DisplayName("DB에 사용자들이 있을 때 개포카드 사용 인원을 찾아 users의 개포 인원과 비교")
    public void findGeapo() {
        List<LocationDto> dtos = logService.usingCardGeapo();

        List<User> res1 = users.stream().filter(u -> u.getCardNumber() < 1000).collect(Collectors.toList());
        List<LocationDto> res2 = dtos.stream().filter(u -> u.getLocation().equals(Location.GAEPO)).collect(Collectors.toList());

        Assertions.assertEquals(res1.size(), res2.size());
    }
}