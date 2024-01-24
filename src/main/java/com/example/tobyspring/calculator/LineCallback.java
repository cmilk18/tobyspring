package com.example.tobyspring.calculator;

public interface LineCallback<T> {
    T doSomethingWithLine(String line, T value);
}
