package com.example.tobyspring.user.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class SimpleConnectionMaker implements ConnectionMaker {
    public Connection makeConnection() throws ClassNotFoundException, SQLException {
        Class.forName("com.mysql.cj.jdbc.Driver");
        Connection c = DriverManager.getConnection("jdbc:mysql://host.docker.internal:3306/test?useSSL=false", "root", "123456");
        return c;
    }


    public void setDriverClass(String driverClass) {
    }

    public void setUrl(String url) {
    }

    public void setUsername(String username) {
    }

    public void setPassword(String password) {
    }
}
