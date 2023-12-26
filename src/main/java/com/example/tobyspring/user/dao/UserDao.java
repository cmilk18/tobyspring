package com.example.tobyspring.user.dao;

import com.example.tobyspring.user.domain.User;

import javax.sql.DataSource;
import java.sql.*;

public class UserDao {

    private DataSource dataSource;

    public void setDataSource(DataSource dataSource){
        this.dataSource = dataSource;
    }

    private final SimpleConnectionMaker simpleConnectionMaker;

    private ConnectionMaker connectionMaker;
    private Connection c;
    private User user;

    public void setConnectionMaker(){
    }

/*
    public UserDao(ConnectionMaker connectionMaker){
       simpleConnectionMaker = new SimpleConnectionMaker();
    }
*/

    private static UserDao INSTANCE;

    UserDao(){
        simpleConnectionMaker = new SimpleConnectionMaker();
        //AnnotationConfigApplicationContext context1 = new AnnotationConfigApplicationContext(DaoFactory.class);
        //this.connectionMaker = context1.getBean("connectionMaker", ConnectionMaker.class);
    }

    public UserDao userDao(){
        UserDao userDao = new UserDao();
        userDao.setConnectionMaker();
        return userDao();
    }



    public static synchronized  UserDao getInstance(){
        if(INSTANCE == null) INSTANCE = new UserDao();
        return INSTANCE;
    }



    public void add(User user) throws ClassNotFoundException, SQLException {
        Connection c = simpleConnectionMaker.makeConnection();
        //Connection c = dataSource.getConnection();

        PreparedStatement ps = c.prepareStatement("insert into users(id,name,password) values(?,?,?)");
        ps.setString(1, user.getId());
        ps.setString(2, user.getName());
        ps.setString(3, user.getPassword());

        ps.executeUpdate();

        ps.close();
        c.close();
    }

    public User get(String id) throws ClassNotFoundException, SQLException{

        //Connection c = simpleConnectionMaker.makeConnection();
        this.c = simpleConnectionMaker.makeConnection();

        PreparedStatement ps = c.prepareStatement("select * from users where id = ?");
        ps.setString(1,id);
        ResultSet rs = ps.executeQuery();
        rs.next();

        /*User user = new User();
        user.setId(rs.getString("id"));
        user.setName(rs.getString("name"));
        user.setPassword(rs.getString("password"));*/

        this.user = new User();
        this.user.setId(rs.getString("id"));
        this.user.setName(rs.getString("name"));
        this.user.setPassword(rs.getString("password"));

        rs.close();
        ps.close();
        c.close();

        return this.user;
    }



}
