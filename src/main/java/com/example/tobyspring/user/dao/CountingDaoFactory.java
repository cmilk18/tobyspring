package com.example.tobyspring.user.dao;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CountingDaoFactory {

    @Bean //오브젝트 생성을 담당하는 IOC용 메소드라는 표시
    public UserDao userDao(){
        return new UserDao();

    }

    @Bean
    public ConnectionMaker connectionMaker(){
        return new CountingConnectionMaker(realConnectionMaker());
    }

    private ConnectionMaker realConnectionMaker() {
        return new SimpleConnectionMaker();
    }
}
