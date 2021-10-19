package com.checkin.CheckIn.dbconnectiontest;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.env.YamlPropertySourceLoader;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

public class mysqlConnectinoTest {

    @DisplayName("1. Get MYSQL ID/PW from env ")
    @Test
    public void getEnv() {
        String mysqlId = System.getenv("MYSQL_ID");
        String mysqlPw = System.getenv("MYSQL_PW");
        Assertions.assertNotNull(mysqlId);
        Assertions.assertNotNull(mysqlPw);
    }

}
