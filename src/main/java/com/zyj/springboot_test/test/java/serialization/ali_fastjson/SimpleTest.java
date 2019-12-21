package com.zyj.springboot_test.test.java.serialization.ali_fastjson;

import com.alibaba.fastjson.JSON;
import com.zyj.springboot_test.test.java.serialization.google_protobuff.protobuf.Peson;
import com.zyj.springboot_test.test.java.serialization.model.Numb;
import com.zyj.springboot_test.test.java.serialization.model.Person;
import redis.clients.jedis.Jedis;

import java.util.ArrayList;

public class SimpleTest {
    public static void main(String[] args) {
        Jedis jedis = new Jedis("localhost", 6320);
        Person build = Person.build();
        Numb numb = Numb.getInstance();

        //实验结果：差不多
//        long before = System.currentTimeMillis();
        ArrayList<String> strings = new ArrayList<>();
        for (int i = 0; i < 10000; i++) {
            strings.add(JSON.toJSONString(Numb.getInstance()));
        }

//        long after = System.currentTimeMillis();
//        System.out.println(after - before);
//
//        long before1 = System.currentTimeMillis();
//        Person person = JSON.parseObject(string, Person.class);
//        long after2 = System.currentTimeMillis();
//        System.out.println(after - before);

        //int 序列化也是差不多
//        long before = System.currentTimeMillis();
//        for (int i = 0; i < 1000000; i++) {
//            String string = JSON.toJSONString(Numb.getInstance());
//        }
//        long after = System.currentTimeMillis();
//        System.out.println(after - before);

        //int 反序列化
        long before = System.currentTimeMillis();
        for (String s:strings) {
            JSON.parseObject(s.getBytes(), Numb.class);
        }
        long after = System.currentTimeMillis();
        System.out.println(after - before);

    }
}
