package com.checkin.CheckIn.config.mybatis;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;

@Configuration
@MapperScan(value = "com.checkin.CheckIn.repository.usermapper", sqlSessionFactoryRef = "userSqlSessionFactory")
@EnableTransactionManagement
public class UserMybatisConfig {
    @Primary
    @Bean(name="userDataSource")
    @ConfigurationProperties(prefix="spring.user.datasource")
    public DataSource userDataSource() {
        return DataSourceBuilder.create().build();
    }

    @Primary
    @Bean(name = "userSqlSessionFactory")
    public SqlSessionFactory userSqlSessionFactory(@Qualifier("userDataSource") DataSource dataSource,
                                                   ApplicationContext applicationContext) throws Exception {
        org.apache.ibatis.session.Configuration configuration = new org.apache.ibatis.session.Configuration();
        SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
        sqlSessionFactoryBean.setDataSource(dataSource);
        sqlSessionFactoryBean.setMapperLocations(applicationContext.getResource("classpath:mapper/UserMapperRepo.xml"));
        return sqlSessionFactoryBean.getObject();
    }

    @Primary
    @Bean(name = "userSqlSessionTemplate")
    public SqlSessionTemplate userSqlSessionTemplate(SqlSessionFactory userSqlSessionFactory) {
        return new SqlSessionTemplate(userSqlSessionFactory);
    }
}