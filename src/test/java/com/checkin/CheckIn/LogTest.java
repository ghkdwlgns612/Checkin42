package com.checkin.CheckIn;

import com.checkin.CheckIn.domain.User;
import com.checkin.CheckIn.repository.UserRepository;
import com.checkin.CheckIn.service.LogService;
import com.checkin.CheckIn.service.dto.LocationDto;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
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

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private LogService logService;

    List<User> users = new ArrayList<>();

    @BeforeAll
    public void setup() {
        addUsers();
    }

    private void addUsers() {
        users.add(User.builder()
                .username("jihuhwan")
                .cardNumber(99L)
                .checkIn(LocalDateTime.now().plusDays(1).withHour(6).withMinute(0).withSecond(0))
                .checkOut(null)
                .intraId(86000L)
                .build());
        users.add(User.builder()
                .username("gpark")
                .cardNumber(1099L)
                .checkIn(LocalDateTime.now().plusDays(1).withHour(6).withMinute(0).withSecond(0))
                .checkOut(LocalDateTime.now().plusDays(1).withHour(11).withMinute(0).withSecond(0))
                .intraId(86021L)
                .build());
        users.add(User.builder()
                .username("jihuhwan1")
                .cardNumber(1089L)
                .checkIn(LocalDateTime.now().plusDays(1).withHour(8).withMinute(0).withSecond(0))
                .checkOut(LocalDateTime.now().plusDays(1).withHour(12).withMinute(0).withSecond(0))
                .intraId(86031L)
                .build());
        users.add(User.builder()
                .username("gpark1")
                .cardNumber(98L)
                .checkIn(LocalDateTime.now().plusDays(1).withHour(8).withMinute(0).withSecond(0))
                .checkOut(null)
                .intraId(86041L)
                .build());
        users.add(User.builder()
                .username("hwang")
                .cardNumber(1100L)
                .checkIn(LocalDateTime.now().plusDays(1).withHour(8).withMinute(0).withSecond(0))
                .checkOut(null)
                .intraId(86051L)
                .build());

        users.stream().forEach(user -> {
            userRepository.save(user);
        });
    }

    @Test
    @DisplayName("레포지토리에서 모든 회원을 조회")
    public void findAllfromRepository() {
        List<User> result = userRepository.findAll();
        Integer usersNumber = users.size();

        Assertions.assertEquals(result.size(), usersNumber);
    }

    @Test
    @DisplayName("LogService에서 모든 회원 조회 후 개포 서초가 맞는지 확인")
    public void findAllBySeoCho() {
        List<LocationDto> result = logService.usingCardAll();

        List<User> res1 = users.stream().filter(u -> u.getCardNumber() >= 1000).collect(Collectors.toList());
        List<LocationDto> res2 = result.stream().filter(u -> u.getLocation().equals("Seocho")).collect(Collectors.toList());

        Assertions.assertEquals(res1.size(), res2.size());
    }

    @Test
    @DisplayName("서초카드 사용 인원 찾기")
    public void findCardSeocho() {
        List<LocationDto> dtos = logService.usingCardSeocho();

        List<User> res1 = users.stream().filter(u -> u.getCardNumber() >= 1000).collect(Collectors.toList());
        List<LocationDto> res2 = dtos.stream().filter(u -> u.getLocation().equals("Seocho")).collect(Collectors.toList());

        Assertions.assertEquals(res1.size(), res2.size());
    }

    @Test
    @DisplayName("개포 인원 찾기")
    public void findGeapo() {
        List<LocationDto> dtos = logService.usingCardGeapo();

        List<User> res1 = users.stream().filter(u -> u.getCardNumber() < 1000).collect(Collectors.toList());
        List<LocationDto> res2 = dtos.stream().filter(u -> u.getLocation().equals("Geapo")).collect(Collectors.toList());

        Assertions.assertEquals(res1.size(), res2.size());
    }
}