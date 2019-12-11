package com.zyj.springboot_test.bean;

import org.springframework.stereotype.Component;

@Component
public class HelloBody {
    private String hello;

    public String getHello() {
        return hello;
    }

    public void setHello(String hello) {
        this.hello = hello;
    }
    public void doSomething1() {
        System.out.println("do1");
    }
    public void doSomething2() {
        System.out.println("do2");
    }
}
