package com.checkin.CheckIn;

import com.checkin.CheckIn.domain.Log;
import com.checkin.CheckIn.repository.logmapper.LogMapper;
import com.checkin.CheckIn.repository.usermapper.UserMapper;
import com.checkin.CheckIn.service.CheckInOutService;
import com.checkin.CheckIn.service.ClusterService;
import com.checkin.CheckIn.service.LogService;
import com.checkin.CheckIn.service.UserService;
import com.checkin.CheckIn.service.dto.NumberOfPeopleDto;
import com.checkin.CheckIn.service.dto.UserResponseDto;
import javassist.NotFoundException;
import javassist.bytecode.DuplicateMemberException;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(value = MethodOrderer.OrderAnnotation.class)
@Slf4j
public class IntegrationTest {
    private UserMapper userMapper;
    private ClusterService clusterService;
    private CheckInOutService checkInOutService;
    private LogService logService;
    private UserService userService;

    @Autowired
    public IntegrationTest(UserMapper userMapper, ClusterService clusterService,
                           CheckInOutService checkInOutService, LogService logService, UserService userService) {
        this.userMapper = userMapper;
        this.clusterService = clusterService;
        this.checkInOutService = checkInOutService;
        this.logService = logService;
        this.userService = userService;
    }

    @Test
    @DisplayName("MYSQL환경변수 설정확인")
    @Order(value = 0)
    public void mysqlEnvTest() {
        String mysqlId = System.getenv("MYSQL_ID");
        String mysqlPw = System.getenv("MYSQL_PW");
        String mysqlUrl1 = System.getenv("MYSQL_URL");
        String mysqlUrl2 = System.getenv("MYSQL_URL_LOG");

        Assertions.assertNotNull(mysqlId);
        Assertions.assertNotNull(mysqlPw);
        Assertions.assertNotNull(mysqlUrl1);
        Assertions.assertNotNull(mysqlUrl2);
    }

    @Test
    @DisplayName("클러스터 입장이 한 번 이상인 교욱생이 출입할 때 DB에 해당 교육생 정보를 잘 업데이트 하는지 확인하는 테스트")
    @Order(value = 1)
    public void checkInTestByUpdateUser() throws DuplicateMemberException, NotFoundException {
        Integer cardNumber = 1500;
        String username = "jihuhwan1";
        UserResponseDto jihuhwan = userService.userStatusService(username);

        Assertions.assertNull(jihuhwan.getCardNumber());

        checkInOutService.checkInService(username, cardNumber);
        jihuhwan = userService.userStatusService(username);

        Assertions.assertEquals(cardNumber, jihuhwan.getCardNumber());
    }

    @Test
    @DisplayName("User DB에 있는 유저가 체크인을 할 경우 Log DB에 잘 저장되는지 확인하는 테스트")
    @Order(value = 2)
    public void checkInLogTest() {
        Log log = logService.lastLog();
        Assertions.assertNotNull(log.getCheckIn());
        Assertions.assertNull(log.getCheckOut());
    }

    @Test
    @DisplayName("클러스터에 입장 한 후 퇴실했을 경우 카드번호가 잘 업데이트 되는지 확인하는 테스트")
    @Order(value = 3)
    public void checkOutTestByUpdateCardNumber() throws NotFoundException {
        Integer cardNumber = 1500;
        String username = "jihuhwan1";
        UserResponseDto jihuhwan = userService.userStatusService(username);

        Assertions.assertEquals(cardNumber, jihuhwan.getCardNumber());

        checkInOutService.checkOutService(username);
        jihuhwan = userService.userStatusService(username);

        Assertions.assertNull(jihuhwan.getCardNumber());
    }

    @Test
    @DisplayName("User DB에 있는 유저가 체크아웃을 할 경우 Log DB에 잘 저장되는지 확인하는 테스트")
    @Order(value = 4)
    public void checkOutLogTest() {
        Log log = logService.lastLog();
        Assertions.assertNotNull(log.getCheckIn());
        Assertions.assertNotNull(log.getCheckOut());
    }

    @Test
    @DisplayName("DB에 새로운 유저를 생성할 때 유저가 중복되는지 확인하는 테스트")
    @Order(value = 5)
    public void createDuplicateUser() {
        String username = "jihuhwan1";
        Assertions.assertThrows(DuplicateMemberException.class, () -> userService.createUser(username));
    }

    @Test
    @DisplayName("DB에 새로운 유저를 생성하고 그 유저를 조회할 때 유저가 잘 저장되었는지 확인하는 테스트")
    @Order(value = 6)
    public void createUser() throws DuplicateMemberException, NotFoundException {
        String username = "testUser";
        userService.createUser(username);

        UserResponseDto result = userService.userStatusService(username);
        Assertions.assertEquals(username, result.getUsername());
    }

    @Test
    @DisplayName("DB에 존재하는 유저의 카드정보를 수정할 때 수정이 잘 되었는지 확인하는 테스트")
    @Order(value = 7)
    public void updateCardNumberUser() throws NotFoundException {  //추후 작성 필요. 원인을 모르겠음.
        String username = "testUser";
        Integer cardNumber = 1234;

        userService.updateUser(cardNumber, username);
        UserResponseDto result = userService.userStatusService(username);
        Assertions.assertEquals(result.getCardNumber(), cardNumber);
    }

    @Test
    @DisplayName("DB에 존재하는 유저를 삭제할 때 삭제가 잘 되었는지 확인하는 테스트")
    @Order(value = 8)
    public void deleteUser() throws NotFoundException {
        String username = "testUser";
        userService.deleteUser(username);

        Assertions.assertThrows(NotFoundException.class, () -> userService.userStatusService(username));
    }

    @Test
    @DisplayName("클러스터 입장이 처음인 교육생이 출입할 때 DB에 새로운 USER를 잘 생성하는지 확인하는 테스트")
    @Order(value = 10)
    public void checkInTestByCreateUser() throws DuplicateMemberException, NotFoundException {
        String username = "42seoul";
        Integer cardNumber = 42;

        checkInOutService.checkInService(username, cardNumber);
        UserResponseDto result = userService.userStatusService(username);
        Assertions.assertEquals(result.getCardNumber(), cardNumber);
        Assertions.assertEquals(result.getUsername(), username);
    }

    @Test
    @DisplayName("DB에 사용자들이 있을 때 레포지토리 로직을 실행하면 서초 인원이 맞는지 확인하는 테스트")
    @Order(value = 11)
    public void peopleSeocho() throws DuplicateMemberException, NotFoundException {
        String username1 = "jihuhwan1";
        String username2 = "gpark1";

        Integer cardNumber1 = 1055;
        Integer cardNumber2 = 1056;

        Integer result = 2; //gpark, jihuhwan;
        checkInOutService.checkInService(username1,cardNumber1);
        checkInOutService.checkInService(username2,cardNumber2);

        Assertions.assertEquals(result, userMapper.countSeochoPeople());
    }

    @Test
    @DisplayName("DB에 사용자들이 있을 때 레포지토리 로직을 실행하면 개포 인원이 맞는지 확인하는 테스트")
    @Order(value = 11)
    public void peopleGaepo() throws DuplicateMemberException, NotFoundException {
        String username1 = "minjupar";
        String username2 = "yunjung";

        Integer cardNumber1 = 43;
        Integer cardNumber2 = 44;

        Integer result = 3; //minjupar, 42seoul, yunjung;
        checkInOutService.checkInService(username1,cardNumber1);
        checkInOutService.checkInService(username2,cardNumber2);

        Assertions.assertEquals(result, userMapper.countGaepoPeople());
    }

    @Test
    @DisplayName("DB에 사용자들이 있을 때 서비스 로직을 실행하면 개포, 서초 인원이 맞는지 확인하는 테스트")
    @Order(value = 12)
    public void peopleGaepoSeocho() {
        NumberOfPeopleDto result = clusterService.countNumberOfPeopleCluster();
        Integer gaepoNum = 3;
        Integer seochoNum = 2;

        Assertions.assertEquals(gaepoNum, result.getGaepo());
        Assertions.assertEquals(seochoNum, result.getSeocho());
    }
}
