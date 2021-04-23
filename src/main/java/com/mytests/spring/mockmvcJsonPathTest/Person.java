package com.mytests.spring.mockmvcJsonPathTest;

/**
 * *
 * <p>Created by irina on 23.04.2021.</p>
 * <p>Project: spring-mockmvc-jsonpath-test</p>
 * *
 */
public class Person {
    String name;

    public Person(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return name;
    }
}
