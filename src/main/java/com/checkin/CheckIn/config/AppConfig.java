package com.checkin.CheckIn.config;

import org.springframework.context.annotation.Configuration;

//SPRING DATA JPA가 아닌 순수 JPA나 JDBC활용 시 사용.
@Configuration
public class AppConfig { //JPA 또는 JDBC사용 시 활성화.
//
//    private final EntityManager em;
//    private final DataSource dataSource;
//
//    public AppConfig(EntityManager em, DataSource dataSource) {
//        this.em = em;
//        this.dataSource = dataSource;
//    }
//
//    @Bean
//    public UserRepository userRepository() {
//        return new JdbcTemplateUserRepository(dataSource);
//    }
//
//    @Bean
//    public LogService logService() {
//        return new LogService(userRepository());
//    }
}
