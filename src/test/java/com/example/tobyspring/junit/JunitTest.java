package com.example.tobyspring.junit;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.HashSet;
import java.util.Set;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration
public class JunitTest {

    @Autowired
    ApplicationContext context;
    static Set<JunitTest> testObjects = new HashSet<JunitTest>();
    static ApplicationContext contextObject = null;

    @Test
    public void test1(){
        assertThat(testObjects,is(not(hasItem(this))));
        testObjects.add(this);
        System.out.println("this : " + this);
        System.out.println("testObject : " + testObjects);
        assertThat(contextObject == null || contextObject == this.context,is(true));
        contextObject = this.context;
    }

    @Test
    public void test2(){
        assertThat(testObjects,is(not(hasItem(this))));
        System.out.println("this : " + this);
        System.out.println("testObject : " + testObjects);
        assertTrue(contextObject == null || contextObject == this.context);
        contextObject = this.context;
    }

    @Test
    public void test3(){
        assertThat(testObjects,is(not(hasItem(this))));
        System.out.println("this : " + this);
        System.out.println("testObject : " + testObjects);
        assertThat(contextObject,either(is(nullValue())).or(is(this.context)));
        contextObject = this.context;
    }


}
