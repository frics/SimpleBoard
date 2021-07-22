package com.example.springbootexample.configuration;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;


@Configuration
@PropertySource("classpath:/application.properties")
@EnableTransactionManagement
public class DBconfiguration {
    @Autowired
    //spring container의 일종이다. 빈의 생성, 사용, 관계, 생명 주기 등을 관리한다.
    private ApplicationContext applicationContext;


    //com.example.springbootexample.configuration 클래스의 메서드 레벨에서만 지정이 가능하다.(com.example.springbootexample.configuration annotation 이 있는 곳)
    //container에 의해 관리 된다.
    @Bean
    //spring.datasource.hikari로 시작하는 모든 설정을 바인딩해준다.
    @ConfigurationProperties(prefix = "spring.datasource.hikari")
    public HikariConfig hikariConfig() {
        return new HikariConfig();
    }

    //매번 쿼리가 들어올때 마다 연결을 맺고 끊는 작업이 일어나면 매우 비효율적이기 때문에 커넥션 풀을 이용
    //커넥션 객체를 미리 생성해두고 요청이 들어오면 제공하고 반환하는 방법을 사용한다
    @Bean
    public DataSource dataSource() {
        return new HikariDataSource(hikariConfig());
    }

    @Bean
    public SqlSessionFactory sqlSessionFactory() throws Exception {
        SqlSessionFactoryBean factoryBean = new SqlSessionFactoryBean();
        factoryBean.setDataSource(dataSource());
        factoryBean.setMapperLocations((applicationContext.getResources("classpath:/mappers/**/*Mapper.xml")));
        factoryBean.setTypeAliasesPackage("com.board.domain");
        factoryBean.setConfiguration(mybatisConfig());

        return factoryBean.getObject();
    }


    @Bean
    public SqlSessionTemplate sqlSession() throws Exception {
        return new SqlSessionTemplate(sqlSessionFactory());
    }

    @Bean
    @ConfigurationProperties(prefix = "mybatis.configuration")
    public org.apache.ibatis.session.Configuration mybatisConfig() {
        return new org.apache.ibatis.session.Configuration();
    }

    @Bean
    public PlatformTransactionManager transactionManager() {
        return new DataSourceTransactionManager(dataSource());
    }
}
