package com.zyj.springboot_test;

import com.zyj.springboot_test.bean.HelloBody;
import com.zyj.springboot_test.bean.Person;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingClass;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;

import java.util.ArrayList;

@SpringBootApplication
//@ComponentScan("com.zyj.springboot_test")
public class SpringbootTestApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringbootTestApplication.class, args);
    }

    @Bean("asd")
    @ConditionalOnBean(Person.class)
    HelloBody helloBody() {
        System.out.println("11111");
        return new HelloBody();
    }
}
