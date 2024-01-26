package com.example.tobyspring.user;

import com.example.tobyspring.exception.DuplicateUserIdException;
import com.example.tobyspring.user.dao.*;
import com.example.tobyspring.user.domain.User;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.JUnitCore;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.jdbc.support.SQLErrorCodeSQLExceptionTranslator;
import org.springframework.jdbc.support.SQLExceptionTranslator;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.sql.DataSource;
import java.sql.*;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = DaoFactory.class)
@DirtiesContext
public class UserDaoTest {


    public static void main(String[] args) {
        JUnitCore.main("com.example.tobyspring.user.UserDaoTest");
    }

    @Autowired
    private ApplicationContext context;

    @Autowired
    public ConnectionMaker connectionMaker;

    private UserDao dao;
    private User user1;
    private User user2;
    private User user3;

    @Before
    public void setUp() throws SQLException {
        //ApplicationContext context = new AnnotationConfigApplicationContext(DaoFactory.class);
        //ApplicationContext context = new GenericXmlApplicationContext("applicationContext.xml.xml");

        /*BasicDataSource dataSource = new BasicDataSource();
        dataSource.setDriverClassName("com.mysql.cj.jdbc.Driver");
        dataSource.setUrl("jdbc:mysql://host.docker.internal:3306/test2?useSSL=false");
        dataSource.setUsername("root");
        dataSource.setPassword("123456");

        this.dao.setDataSource(dataSource);*/
        this.dao = this.context.getBean("userDaoHibernate", UserDao.class);


        this.user1 = new User("sin","신호정","0214");

        this.user2 = new User("kang","강준희","0218");

        this.user3 = new User("kim","김강","0111");

        System.out.println("this.context : "+this.context);
        System.out.println("this : "+this);
    }



    @Test
    public void addAndGet() throws SQLException, ClassNotFoundException, EmptyResultDataAccessException {


        dao.deleteAll();
        assertThat(dao.getCount(),is(0));

        dao.add(user1);
        assertThat(dao.getCount(),is(1));

        dao.add(user2);
        assertThat(dao.getCount(),is(2));

        dao.add(user3);
        assertThat(dao.getCount(),is(3));


        System.out.println(user1.getId() + "등록 성공");

        User user4 = dao.get(user1.getId());
        System.out.println("user4.getName() = " + user4.getName());
        System.out.println("user4.getPassword() = " + user4.getPassword());

        System.out.println("user4.getId() = " + user4.getId()+" 조회성공");

        assertThat(user4.getName(),is(user1.getName()));
        assertThat(user4.getPassword(),is(user1.getPassword()));


    }

    @Test(expected = EmptyResultDataAccessException.class)
    public void getUserFailure() throws SQLException, ClassNotFoundException, EmptyResultDataAccessException {

        dao.deleteAll();
        assertThat(dao.getCount(),is(0));
        dao.get("unknown_id");
    }
    
    @Test
    public void printDaoFactoryObject(){
        DaoFactory factory = new DaoFactory();
        UserDao dao1 = factory.userDaoHibernate();
        UserDao dao2 = factory.userDaoHibernate();

        ApplicationContext context = new AnnotationConfigApplicationContext(DaoFactory.class);

        UserDao dao3 = context.getBean("userDaoHibernate",UserDao.class);
        UserDao dao4 = context.getBean("userDaoHibernate",UserDao.class);

        System.out.println("dao1 = " + dao1);
        System.out.println("dao2 = " + dao2);
        System.out.println("dao3 = " + dao3);
        System.out.println("dao4 = " + dao4);
        System.out.println(dao3==dao4);
        System.out.println(dao3==dao1);
    }

    @Test
    public void printDaoFactoryObjectwithSpring(){
        ApplicationContext context = new AnnotationConfigApplicationContext(DaoFactory.class);

        UserDao dao3 = context.getBean("userDaoHibernate",UserDao.class);
        UserDao dao4 = context.getBean("userDaoHibernate",UserDao.class);

        System.out.println("dao3 = " + dao3);
        System.out.println("dao4 = " + dao4);
    }

    @Test
    public void getAll() throws SQLException, ClassNotFoundException {
        dao.deleteAll();/*
        this.user1 = new User("sin","신호정","0214");

        this.user2 = new User("kang","강준희","0218");

        this.user3 = new User("kim","김강","0111");*/

        List<User> users0 = dao.getAll();
        assertThat(users0.size(),is(0));

        dao.add(user1);
        List<User> users1 = dao.getAll();
        assertThat(users1.size(),is(1));
        checkSameUser(user1,users1.get(0));

        dao.add(user2);
        List<User> users2 = dao.getAll();
        assertThat(users2.size(),is(2));
        checkSameUser(user1,users2.get(1));
        checkSameUser(user2,users2.get(0));

        dao.add(user3);
        List<User> users3 = dao.getAll();
        assertThat(users3.size(),is(3));
        checkSameUser(user3,users3.get(1));
        checkSameUser(user2,users3.get(0));
        checkSameUser(user1,users3.get(2));



    }

    private void checkSameUser(User user1,User user2){
        assertThat(user1.getId(),is(user2.getId()));
        assertThat(user1.getName(),is(user2.getName()));
        assertThat(user1.getPassword(),is(user2.getPassword()));
    }

    @Test(expected = SQLIntegrityConstraintViolationException.class)
    public void duplicatedKey() throws SQLException, ClassNotFoundException {
        dao.deleteAll();

        dao.add(user1);
        dao.add(user1);
    }

    /*@Test
    public void sqlExceptionTranslate() throws SQLException, ClassNotFoundException {
        dao.deleteAll();
        try{
            dao.add(user1);
            dao.add(user1);
        }catch(DuplicateKeyException ex){
            SQLException sqlException = (SQLException)ex.getRootCause();
            SQLExceptionTranslator set = new SQLErrorCodeSQLExceptionTranslator(String.valueOf(this.connectionMaker));
            assertThat(set.translate(null,null,sqlException),is(SQLIntegrityConstraintViolationException.class));

        }
    }*/

}
