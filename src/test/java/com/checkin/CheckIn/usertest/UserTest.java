package com.checkin.CheckIn.usertest;

import com.checkin.CheckIn.domain.User;
import com.checkin.CheckIn.repository.UserMapper;
import com.checkin.CheckIn.service.UserService;
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
public class UserTest {

    @Autowired
    private UserService userService;

    private User jihuhwan;
    private User gpark;

    @BeforeAll
    public void init() {
        jihuhwan = User.builder()
                .username("jihuhwan")
                .intraId(86991L)
                .checkIn(LocalDateTime.of(2021,10,1,13,0))
                .checkOut(LocalDateTime.of(2021,10,1,20,0))
                .cardNumber(13)
                .build();

        gpark = User.builder()
                .username("gpark")
                .intraId(86973L)
                .checkIn(LocalDateTime.of(2021,10,1,9,0))
                .checkOut(LocalDateTime.of(2021,10,1,12,0))
                .cardNumber(12)
                .build();
    }

    @Test
    @DisplayName("DB에 유저들이 주어졌을 때 jihuhwan이란 이름을 가진 유저의 정보를 가져오는 테스트")
    public void callUserInfoJihuhwan() throws NotFoundException {
        String username = jihuhwan.getUsername();
        Integer cardNumber = jihuhwan.getCardNumber();

        UserResponseDto result = userService.userInfoService("jihuhwan");

        Assertions.assertEquals(username, result.getUserId());
        Assertions.assertEquals(cardNumber, result.getCardNumber());
    }

    @Test
    @DisplayName("DB에 유저들이 주어졌을 때 jihuhwan이란 이름을 가져와 gpark의 정보와 비교하는 테스트")
    public void callUserInfoGpark() throws NotFoundException {
        String username = gpark.getUsername();
        Integer cardNumber = gpark.getCardNumber();

        UserResponseDto result = userService.userInfoService("jihuhwan");

        Assertions.assertNotEquals(username,result.getUserId());
        Assertions.assertNotEquals(cardNumber, result.getCardNumber());
    }

    @Test
    @DisplayName("DB에 새로운 유저를 생성하고 그 유저를 조회할 때 유저가 잘 저장되었는지 확인하는 테스트")
    public void createUser() throws NotFoundException, DuplicateMemberException {
        String name =  "minjupar100";
        UserResponseDto create_result = userService.createUser(name);

        UserResponseDto select_result = userService.userInfoService(name);

        Assertions.assertEquals(create_result.getUserId(), select_result.getUserId());
    }

    @Test
    @DisplayName("DB에 새로운 유저를 생성할 때 유저가 중복되는지 확인하는 테스트")
    public void createDuplicateUser() throws NotFoundException, DuplicateMemberException {
        String name = "gpark";
        Assertions.assertThrows(DuplicateMemberException.class, () -> {
            userService.createUser(name);
        });
    }

    @Test
    @DisplayName("DB에 존재하는 유저의 카드정보를 수정할 때 수정이 잘 되었는지 확인하는 테스트")
    public void updateCardNumberUser() throws NotFoundException {
        Integer cardNumber = 1200;
        String username = "jihuhwan";
        userService.updateUser(cardNumber,username);

        UserResponseDto result = userService.userInfoService(username);

        Assertions.assertEquals(cardNumber, result.getCardNumber());
    }

    @Test
    @DisplayName("DB에 존재하는 유저를 삭제할 때 삭제가 잘 되었는지 확인하는 테스트")
    public void deleteUser() throws DuplicateMemberException, NotFoundException {
        String name =  "minjupar100";
        userService.deleteUser(name);

        Assertions.assertThrows(NotFoundException.class, () -> {
            userService.userInfoService(name);
        });
    }
}
