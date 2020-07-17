package com.zyj.springboot_test.test.java.testThread.concurrentCollection;

import java.util.HashMap;
import java.util.function.BiFunction;

public class TestMap {
    public static void main(String[] args) {
        HashMap<String, String> map = new HashMap<>();
        System.out.println("================putIfAbsent=================");
        map.putIfAbsent("1", "1_2");//如果不存在存入
        map.putIfAbsent("1", "1");
        System.out.println(map.get("1"));

        System.out.println("================merge=================");
        map.merge("1", "2",String::concat);//存在则组合，不存在则插入
        map.merge("2", "2",String::concat);
        map.merge("1", "3", new BiFunction<String, String, String>() {
            @Override
            public String apply(String s, String s2) {
                return s+s2;
            }
        });
        System.out.println(map.get("1"));
        System.out.println(map.get("2"));

        System.out.println("================compute=================");

        HashMap<String, Integer> map2 = new HashMap<>();
        map2.put("1", 1);
        map2.compute("1", new BiFunction<String, Integer, Integer>() {//如果元素不存在，会报控指针
            @Override
            public Integer apply(String s, Integer integer) {
                return integer+1;
            }
        });

        map2.computeIfPresent("1", new BiFunction<String, Integer, Integer>() {//如果元素不存在，就不会计算
            @Override
            public Integer apply(String s, Integer integer) {
                return integer+1;
            }
        });
        System.out.println(map2.get("1"));
        map2.put("1", 1);
        System.out.println(map2.get("1"));

    }
}
