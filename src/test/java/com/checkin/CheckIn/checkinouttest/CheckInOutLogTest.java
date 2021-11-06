package com.checkin.CheckIn.checkinouttest;

import com.checkin.CheckIn.domain.Log;
import com.checkin.CheckIn.service.CheckInOutService;
import com.checkin.CheckIn.service.LogService;
import com.checkin.CheckIn.service.UserService;
import com.checkin.CheckIn.service.dto.CheckInOutResponseDto;
import javassist.NotFoundException;
import javassist.bytecode.DuplicateMemberException;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;


@SpringBootTest
@ExtendWith(SpringExtension.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@Slf4j
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class CheckInOutLogTest {

    @Autowired
    private CheckInOutService checkInOutService;

    @Autowired
    private LogService logService;

    @Test
    @DisplayName("User DB에 있는 유저가 체크인을 할 경우 Log DB에 잘 저장되는지 확인하는 테스트")
    @Order(1)
    public void checkInLogTest() throws NotFoundException, DuplicateMemberException {
        //given
        String username = "jihuhwan";
        Integer cardNumber = 1234;
        //when
        CheckInOutResponseDto checkInOutResponseDto = checkInOutService.checkInService(username, cardNumber);
        //then
        Log log = logService.lastLog();
        Assertions.assertNotNull(log.getCheckIn());
        Assertions.assertNull(log.getCheckOut());
    }

    @Test
    @DisplayName("User DB에 있는 유저가 체크아웃을 할 경우 Log DB에 잘 저장되는지 확인하는 테스트")
    @Order(2)
    public void checkOutLogTest() {
        //given
        String username = "jihuhwan";
        //when
        CheckInOutResponseDto checkInOutResponseDto = checkInOutService.checkOutService(username);
        //then
        Log log = logService.lastLog();
        Assertions.assertNotNull(log.getCheckIn());
        Assertions.assertNotNull(log.getCheckOut());
    }
}
