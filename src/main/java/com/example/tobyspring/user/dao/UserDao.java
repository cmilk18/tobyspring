package com.example.tobyspring.user.dao;

import com.example.tobyspring.user.domain.User;

import java.sql.*;

public class UserDao {

    public void add(User user) throws ClassNotFoundException, SQLException {
        Class.forName("com.mysql.cj.jdbc.Driver");
        Connection c = DriverManager.getConnection("jdbc:mysql://host.docker.internal:3306/test?useSSL=false", "root", "123456");

        PreparedStatement ps = c.prepareStatement("insert into users(id,name,password) values(?,?,?)");
        ps.setString(1, user.getId());
        ps.setString(2, user.getName());
        ps.setString(3, user.getPassword());

        ps.executeUpdate();

        ps.close();
        c.close();
    }

    public User get(String id) throws ClassNotFoundException, SQLException{
        Class.forName("com.mysql.cj.jdbc.Driver");
        Connection c = DriverManager.getConnection("jdbc:mysql://host.docker.internal:3306/test?useSSL=false", "root", "123456");

        PreparedStatement ps = c.prepareStatement("select * from users where id = ?");
        ps.setString(1,id);
        ResultSet rs = ps.executeQuery();
        rs.next();
        User user = new User();
        user.setId(rs.getString("id"));
        user.setName(rs.getString("name"));
        user.setPassword(rs.getString("password"));

        rs.close();
        ps.close();
        c.close();

        return user;
    }

    public static void main(String[] args) throws ClassNotFoundException, SQLException{
        UserDao dao = new UserDao();
/*
        User user = new User();

        user.setId("Kang");
        user.setName("강준희");
        user.setPassword("married");

        dao.add(user);

        System.out.println(user.getId() + "등록 성공");*/

        User user2 = dao.get("Kang");
        System.out.println("user.getName() = " + user2.getName());
        System.out.println("user2.getPassword() = " + user2.getPassword());

        System.out.println("user2.getId() = " + user2.getId()+" 조회성공");

    }
}
