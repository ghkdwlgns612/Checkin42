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
@MapperScan(value = "com.checkin.CheckIn.repository.logmapper", sqlSessionFactoryRef = "logSqlSessionFactory")
@EnableTransactionManagement
public class LogMybatisConfig {
    @Bean(name="logDataSource")
    @ConfigurationProperties(prefix="spring.log.datasource")
    public DataSource logDataSource() {
        return DataSourceBuilder.create().build();
    }

    @Bean(name = "logSqlSessionFactory")
    public SqlSessionFactory logSqlSessionFactory(@Qualifier("logDataSource") DataSource dataSource,
                                                  ApplicationContext applicationContext) throws Exception {
        SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
        sqlSessionFactoryBean.setDataSource(dataSource);
        sqlSessionFactoryBean.setMapperLocations(applicationContext.getResource("classpath:mapper/LogMapperRepo.xml"));
        return sqlSessionFactoryBean.getObject();
    }

    @Bean(name = "logSqlSessionTemplate")
    public SqlSessionTemplate userSqlSessionTemplate(SqlSessionFactory logSqlSessionFactory) {
        return new SqlSessionTemplate(logSqlSessionFactory);
    }
}