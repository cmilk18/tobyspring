package com.example.tobyspring.user.dao;

import com.example.tobyspring.user.domain.User;

import java.sql.SQLException;
import java.util.List;

public interface UserDao {
    void add(User user) throws SQLException, ClassNotFoundException;
    User get(String id) throws SQLException, ClassNotFoundException, EmptyResultDataAccessException;
    List<User> getAll() throws SQLException, ClassNotFoundException;;
    void deleteAll() throws SQLException, ClassNotFoundException;;
    int getCount() throws SQLException, ClassNotFoundException;;
}
