package com.example.tobyspring.user;

import com.example.tobyspring.user.dao.ConnectionMaker;
import com.example.tobyspring.user.dao.DaoFactory;
import com.example.tobyspring.user.dao.SimpleConnectionMaker;
import com.example.tobyspring.user.dao.UserDao;
import com.example.tobyspring.user.domain.User;
import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.GenericApplicationContext;
import org.springframework.context.support.GenericXmlApplicationContext;

import java.sql.SQLException;

public class UserDaoTest {
    public static void main(String[] args) throws ClassNotFoundException, SQLException {

        ApplicationContext context = new AnnotationConfigApplicationContext(DaoFactory.class);
        //ApplicationContext context = new GenericXmlApplicationContext("applicationContext.xml.xml");

        UserDao dao = context.getBean("userDao",UserDao.class);
        User user = new User();

        user.setId("Shin");
        user.setName("신호정");
        user.setPassword("hi");

        dao.add(user);

        System.out.println(user.getId() + "등록 성공");

        User user2 = dao.get("Kang");
        System.out.println("user2.getName() = " + user2.getName());
        System.out.println("user2.getPassword() = " + user2.getPassword());

        System.out.println("user2.getId() = " + user2.getId()+" 조회성공");

    }
    
    @Test
    void printDaoFactoryObject(){
        DaoFactory factory = new DaoFactory();
        UserDao dao1 = factory.userDao();
        UserDao dao2 = factory.userDao();

        ApplicationContext context = new AnnotationConfigApplicationContext(DaoFactory.class);

        UserDao dao3 = context.getBean("userDao",UserDao.class);
        UserDao dao4 = context.getBean("userDao",UserDao.class);

        System.out.println("dao1 = " + dao1);
        System.out.println("dao2 = " + dao2);
        System.out.println("dao3 = " + dao3);
        System.out.println("dao4 = " + dao4);
        System.out.println(dao3==dao4);
        System.out.println(dao3==dao1);
    }

    @Test
    void printDaoFactoryObjectwithSpring(){
        ApplicationContext context = new AnnotationConfigApplicationContext(DaoFactory.class);

        UserDao dao3 = context.getBean("userDao",UserDao.class);
        UserDao dao4 = context.getBean("userDao",UserDao.class);

        System.out.println("dao3 = " + dao3);
        System.out.println("dao4 = " + dao4);
    }
}
