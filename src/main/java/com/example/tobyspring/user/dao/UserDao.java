package com.example.tobyspring.user.dao;

import com.example.tobyspring.user.domain.User;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDao implements StatementStrategy{

    private DataSource dataSource;

    private JdbcTemplate jdbcTemplate;


    public void setDataSource(SimpleConnectionMaker simpleConnectionMaker){
        this.jdbcTemplate = new JdbcTemplate((DataSource) simpleConnectionMaker);
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

    private JdbcContext jdbcContext;
    public void setJdbcContext(SimpleConnectionMaker simpleConnectionMaker){
        this.jdbcContext = new JdbcContext(simpleConnectionMaker);

    }



    public void add(final User user) throws ClassNotFoundException, SQLException {
        jdbcContextWithStatementStrategy(
                new StatementStrategy(){

                public PreparedStatement makePreparedStatement(Connection c) throws SQLException{

                    PreparedStatement ps = c.prepareStatement("insert into users(id,name,password)values(?,?,?)");

                    ps.setString(1,user.getId());
                    ps.setString(2,user.getName());
                    ps.setString(3,user.getPassword());

                    return ps;
                    }
                }
        );

        /*Connection c = simpleConnectionMaker.makeConnection();
        //Connection c = dataSource.getConnection();

        PreparedStatement ps = c.prepareStatement("insert into users(id,name,password) values(?,?,?)");
        ps.setString(1, user.getId());
        ps.setString(2, user.getName());
        ps.setString(3, user.getPassword());

        ps.executeUpdate();

        ps.close();
        c.close();*/
    }

    public User get(String id) throws ClassNotFoundException, SQLException, EmptyResultDataAccessException {

        //Connection c = simpleConnectionMaker.makeConnection();
        this.c = simpleConnectionMaker.makeConnection();

        PreparedStatement ps = c.prepareStatement("select * from users where id = ?");
        ps.setString(1,id);
        ResultSet rs = ps.executeQuery();

        if(rs.next()){
            this.user = new User();
            this.user.setId(rs.getString("id"));
            this.user.setName(rs.getString("name"));
            this.user.setPassword(rs.getString("password"));
        }

        /*User user = new User();
        user.setId(rs.getString("id"));
        user.setName(rs.getString("name"));
        user.setPassword(rs.getString("password"));*/

        rs.close();
        ps.close();
        c.close();

        if(user == null) throw new EmptyResultDataAccessException(1);

        return this.user;
    }
    private void executeSql(final String query) throws SQLException, ClassNotFoundException {
        jdbcContextWithStatementStrategy(//jdbcContextWithStatementStrategy
                new StatementStrategy() {
                    @Override
                    public PreparedStatement makePreparedStatement(Connection c) throws SQLException {
                        return c.prepareStatement("delete from users");
                    }
                }
        );
    }

    public void deleteAll() throws SQLException, ClassNotFoundException {

        //this.jdbcTemplate.update("delete from users");
        executeSql("delete from users");
        /*
       StatementStrategy st = new DeleteAllStatement();
       jdbcContextWithStatementStrategy(st);
*/
        /* Connection c = null;
        PreparedStatement ps = null;

        try{
            c = simpleConnectionMaker.makeConnection();
            ps = makeStatement(c);
            ps.executeUpdate();
        }catch (SQLException e){
            throw e;
        }finally {
            if (ps != null) {
                try {
                    ps.close();
                } catch (SQLException e) {
                }
            }
            if (c != null) {
                try {
                    c.close();
                } catch (SQLException e) {
                }
            }
        }*/
    }

    public void jdbcContextWithStatementStrategy(StatementStrategy stmt) throws SQLException, ClassNotFoundException {
        Connection c = null;
        PreparedStatement ps = null;

        try{
            c=simpleConnectionMaker.makeConnection();
            ps= stmt.makePreparedStatement(c);
            ps.executeUpdate();
        } catch (SQLException | ClassNotFoundException e) {
            throw e;
        }finally {
            if (ps != null) {
                try {
                    ps.close();
                } catch (SQLException e) {
                }
            }
            if (c != null) {
                try {
                    c.close();
                } catch (SQLException e) {
                }
            }
        }
    }

    private PreparedStatement makeStatement(Connection c)throws SQLException{
        PreparedStatement ps;
        ps = c.prepareStatement("delete from users");
        return ps;
    }

    public int getCount() throws SQLException, ClassNotFoundException {
        Connection c = null;
        PreparedStatement ps = null;
        ResultSet rs =  null;
        try {
            c = simpleConnectionMaker.makeConnection();
            ps = c.prepareStatement("SELECT count(*) from users");
            rs = ps.executeQuery();
            rs.next();
            return rs.getInt(1);

        }catch (SQLException e){
            throw e;
        }finally {
            if(rs!=null){
                try{
                    rs.close();
                }catch(SQLException e){

                }
            }
            if(ps!=null){
                try{
                    ps.close();
                }catch(SQLException e){

                }
            }
            if(c!=null){
                try{
                    c.close();
                }catch(SQLException e){

                }
            }
        }
    }


    @Override
    public PreparedStatement makePreparedStatement(Connection c) throws SQLException {
        PreparedStatement ps = c.prepareStatement("delete from users");
        return ps;
    }

    public List<User> getAll() throws SQLException {
        Connection c = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            c = simpleConnectionMaker.makeConnection();
            ps = c.prepareStatement("SELECT * FROM users ORDER BY id");
            rs = ps.executeQuery();

            List<User> userList = new ArrayList<>();
            while (rs.next()) {
                User user = new User();
                user.setId(rs.getString("id"));
                user.setName(rs.getString("name"));
                user.setPassword(rs.getString("password"));
                userList.add(user);
            }
            System.out.println("userList = " + userList);
            return userList;
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        } finally {
            // Close resources in a finally block to ensure they are closed even if an exception is thrown.
            if (rs != null) {
                rs.close();
            }
            if (ps != null) {
                ps.close();
            }
            if (c != null) {
                c.close();
            }
        }
    }


}
