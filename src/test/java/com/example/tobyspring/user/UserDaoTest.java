package com.example.tobyspring.user;

import com.example.tobyspring.user.dao.ConnectionMaker;
import com.example.tobyspring.user.dao.DaoFactory;
import com.example.tobyspring.user.dao.SimpleConnectionMaker;
import com.example.tobyspring.user.dao.UserDao;
import com.example.tobyspring.user.domain.User;

import java.sql.SQLException;

public class UserDaoTest {
    public static void main(String[] args) throws ClassNotFoundException, SQLException {


        UserDao dao = new DaoFactory().userDao();

        User user = new User();

        user.setId("Kang");
        user.setName("강준희");
        user.setPassword("hi");

        dao.add(user);

        System.out.println(user.getId() + "등록 성공");

        User user2 = dao.get("Kang");
        System.out.println("user.getName() = " + user2.getName());
        System.out.println("user2.getPassword() = " + user2.getPassword());

        System.out.println("user2.getId() = " + user2.getId()+" 조회성공");

    }
}
