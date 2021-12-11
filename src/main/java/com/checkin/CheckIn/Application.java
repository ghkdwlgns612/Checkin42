package com.checkin.CheckIn;

import com.checkin.CheckIn.utils.resource.JWTSecurityResource;
import com.checkin.CheckIn.utils.resource.OAuth42Resource;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties({JWTSecurityResource.class, OAuth42Resource.class})
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

}
