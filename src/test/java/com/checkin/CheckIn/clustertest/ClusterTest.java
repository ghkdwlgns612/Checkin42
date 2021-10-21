package com.checkin.CheckIn.clustertest;

import com.checkin.CheckIn.domain.User;
import com.checkin.CheckIn.repository.UserMapper;
import com.checkin.CheckIn.service.ClusterService;
import com.checkin.CheckIn.service.dto.NumberOfPeopleDto;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@Slf4j
public class ClusterTest {
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private ClusterService clusterService;

    List<User> users = new ArrayList<>();

    @BeforeAll
    public void setup() {
        addUsers();
    }

    private void addUsers() {
        users.add(User.builder()
                .username("gpark")
                .cardNumber(12L)
                .checkIn(LocalDateTime.of(2021,10,1,9,0,0))
                .checkOut(LocalDateTime.of(2021,10,1,12,0,0))
                .intraId(86973L)
                .build());
        users.add(User.builder()
                .username("jihuhwan")
                .cardNumber(13L)
                .checkIn(LocalDateTime.of(2021,10,1,13,0,0))
                .checkOut(LocalDateTime.of(2021,10,1,20,0,0))
                .intraId(86991L)
                .build());
        users.add(User.builder()
                .username("yunjung")
                .cardNumber(14L)
                .checkIn(LocalDateTime.of(2021,10,1,10,0,0))
                .checkOut(LocalDateTime.of(2021,10,1,23,59,59))
                .intraId(86993L)
                .build());
        users.add(User.builder()
                .username("minjupar")
                .cardNumber(15L)
                .checkIn(LocalDateTime.of(2021,10,1,9,0,0))
                .checkOut(LocalDateTime.of(2021,10,1,22,0,0))
                .intraId(86982L)
                .build());
        users.add(User.builder()
                .username("gpark1")
                .cardNumber(1012L)
                .checkIn(LocalDateTime.of(2021,10,1,9,0,0))
                .checkOut(LocalDateTime.of(2021,10,1,12,0,0))
                .intraId(86111L)
                .build());
        users.add(User.builder()
                .username("jihuhwan1")
                .cardNumber(1013L)
                .checkIn(LocalDateTime.of(2021,10,1,13,0,0))
                .checkOut(LocalDateTime.of(2021,10,1,20,0,0))
                .intraId(86222L)
                .build());
        users.add(User.builder()
                .username("yunjung1")
                .cardNumber(1014L)
                .checkIn(LocalDateTime.of(2021,10,1,10,0,0))
                .checkOut(LocalDateTime.of(2021,10,1,23,59,59))
                .intraId(86333L)
                .build());
        users.add(User.builder()
                .username("minjupar1")
                .cardNumber(1015L)
                .checkIn(LocalDateTime.of(2021,10,1,9,0,0))
                .checkOut(LocalDateTime.of(2021,10,1,22,0,0))
                .intraId(86444L)
                .build());
    }

    @Test
    @DisplayName("DB에 사용자들이 있을 때 서비스 로직을 실행하면 개포, 서초 인원이 맞는지 확인하는 테스트")
    public void peopleGaepoSeocho() {
        Integer seochoSize = 4;
        Integer gaepoSize = 4;

        NumberOfPeopleDto result = clusterService.countNumberOfPeopleCluster();

        Assertions.assertEquals(seochoSize, result.getSeocho());
        Assertions.assertEquals(gaepoSize, result.getGaepo());
    }

    @Test
    @DisplayName("DB에 사용자들이 있을 때 레포지토리 로직을 실행하면 서초 인원이 맞는지 확인하는 테스트")
    public void peopleSeocho() {
        Integer seochoSize = 4;

        Integer result = userMapper.countSeochoPeople();

        Assertions.assertEquals(seochoSize, result);
    }

    @Test
    @DisplayName("DB에 사용자들이 있을 때 레포지토리 로직을 실행하면 개포 인원이 맞는지 확인하는 테스트")
    public void peopleGaepo() {
        Integer gaepoSize = 4;

        Integer result = userMapper.countGaepoPeople();

        Assertions.assertEquals(gaepoSize, result);
    }
}
