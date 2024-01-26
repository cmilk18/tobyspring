package com.example.tobyspring.user.dao;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration //에플리케이션 컨텍스트 또는 빈팩토리가 사용할 설정정보라는 표시
public class DaoFactory {

    @Bean //오브젝트 생성을 담당하는 IOC용 메소드라는 표시
    public UserDaoHibernate userDaoHibernate(){
        return new UserDaoHibernate();

    }

    @Bean
    public ConnectionMaker connectionMaker() {
        return new SimpleConnectionMaker();
    }
}
