package com.checkin.CheckIn.Oauth2LoginTest;

import com.checkin.CheckIn.utils.OAuthUtils;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class Oauth2Tests {
    @Autowired
    private OAuthUtils oAuthUtils;

    @DisplayName("Check in memory OAuth is Set")
    @Test
    public void testOAuthBean() {
        oAuthUtils.showOauths();
    }
}
