package com.zyj.springboot_test.controller;

import com.zyj.springboot_test.bean.HelloBody;
import com.zyj.springboot_test.bean.Person;
import com.zyj.springboot_test.bean.Yml;
import com.zyj.springboot_test.test.mysql.mapper.SingelTableMapper;
import com.zyj.springboot_test.test.mysql.model.SingleTable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController("/test")
public class Hello {
    @Autowired
    private Yml yml;

    @Autowired
    private SingelTableMapper singelTableMapper;


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

    @RequestMapping("/insert")
    public void insert() {
        for (int i = 0; i < 10; i++) {
            singelTableMapper.insert1(new SingleTable(
                    "key1_" + i,
                    i,
                    "key3_" + i,
                    "key_part1" + i,
                    "key_part2" + i,
                    "key_part3" + i,
                    "common_field"

            ));
        }

    }
}
