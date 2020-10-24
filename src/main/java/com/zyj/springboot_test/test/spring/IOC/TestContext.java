package com.zyj.springboot_test.test.spring.IOC;

import com.zyj.springboot_test.bean.Person;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class TestContext {
    public static void main(String[] args) {
        ClassPathXmlApplicationContext classPathXmlApplicationContext = new ClassPathXmlApplicationContext("applicationContext.xml");
        Person person = classPathXmlApplicationContext.getBean("person", Person.class);
        System.out.println(person);


    }
}
