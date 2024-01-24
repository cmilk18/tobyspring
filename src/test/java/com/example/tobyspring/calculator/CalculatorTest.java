package com.example.tobyspring.calculator;


import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.net.URL;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class CalculatorTest {
    Calculator calculator;
    String numFilepath;

    @Before
    public void setUp(){
        this.calculator = new Calculator();
        this.numFilepath = getClass().getResource("numbers.txt").getPath();
    }
    @Test
    public void sumOfNumbers() throws IOException{
        Calculator calculator = new Calculator();
        assertThat(calculator.calSum(this.numFilepath),is(10));
    }
    @Test
    public void multiplyOfNumbers() throws IOException{
        assertThat(calculator.calMultiply(this.numFilepath),is(24));
    }
    @Test
    public void concatenateStrings() throws IOException{
        assertThat(calculator.concatenate(this.numFilepath),is("1234"));
    }
}
