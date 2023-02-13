package com.zyj.springboot_test.controller;

import com.zyj.springboot_test.bean.HelloBody;
import com.zyj.springboot_test.bean.Yml;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController("/test")
public class Hello {
    @Autowired
    public Yml yml;



//    @Autowired
//    private Person zyj;

    @RequestMapping("/hello")
    public HelloBody hello() {
        HelloBody helloBody = new HelloBody();
        helloBody.setHello("你好");
        return helloBody;
    }

    @RequestMapping("/yml")
    public Yml testYml() {
        return yml;
    }
//    @RequestMapping("/zyj")
////    public Person testBean() {
////        return zyj;
////    }
}
