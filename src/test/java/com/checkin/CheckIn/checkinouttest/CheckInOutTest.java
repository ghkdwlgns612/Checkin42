package com.checkin.CheckIn.checkinouttest;

import com.checkin.CheckIn.domain.User;
import com.checkin.CheckIn.service.CheckInOutService;
import com.checkin.CheckIn.service.UserService;
import com.checkin.CheckIn.service.dto.CheckInOutResponseDto;
import com.checkin.CheckIn.service.dto.UserResponseDto;
import javassist.NotFoundException;
import javassist.bytecode.DuplicateMemberException;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDateTime;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@Slf4j
public class CheckInOutTest {

    @Autowired
    private CheckInOutService checkInOutService;
    @Autowired
    private UserService userService;

    private User testUser; //처음 방문하는 유저

    @BeforeAll
    public void init() {
        testUser = User.builder()
                .cardNumber(1039)
                .intraId(100000L)
                .checkIn(LocalDateTime.now())
                .username("testUser")
                .checkOut(null)
                .build();
    }

    @Test
    @DisplayName("클러스터 입장이 처음인 교육생이 출입할 때 DB에 새로운 USER를 잘 생성하는지 확인하는 테스트")
    public void checkInTestByCreateUser() throws DuplicateMemberException, NotFoundException {
        checkInOutService.checkInService(testUser.getUsername(), testUser.getCardNumber());

        UserResponseDto responseDto = userService.userStatusService(testUser.getUsername());

        Assertions.assertEquals(responseDto.getCardNumber(), testUser.getCardNumber());
    }

    @Test
    @DisplayName("클러스터 입장이 한 번 이상인 교욱생이 출입할 때 DB에 해당 교육생 정보를 잘 업데이트 하는지 확인하는 테스트")
    public void checkInTestByUpdateUser() throws DuplicateMemberException, NotFoundException {
        int cardNumber = 150;
        UserResponseDto responseDto1 = userService.userStatusService("jihuhwan");

        Assertions.assertNotEquals(cardNumber,responseDto1.getCardNumber());

        checkInOutService.checkInService("jihuhwan",cardNumber);

        UserResponseDto responseDto2 = userService.userStatusService("jihuhwan");

        Assertions.assertEquals(responseDto2.getCardNumber(), cardNumber);
    }

    @Test
    @DisplayName("클러스터에 입장 한 후 퇴실했을 경우 카드번호가 잘 업데이트 되는지 확인하는 테스트")
    public void checkOutTestByUpdateCardNumber() throws NotFoundException {
        CheckInOutResponseDto result = checkInOutService.checkOutService("testUser");

        Assertions.assertNull(result.getCardNumber());
    }
}