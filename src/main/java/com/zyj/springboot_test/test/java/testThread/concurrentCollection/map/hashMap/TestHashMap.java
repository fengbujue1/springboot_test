package com.zyj.springboot_test.test.java.testThread.concurrentCollection.map.hashMap;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.function.BiFunction;

public class TestHashMap {
    public static void main(String[] args) {
        testHashMap();
    }
    public static void testHashMap() {
        HashMap<String, String> map = new HashMap<>();
        System.out.println("================putIfAbsent=================");
        String s = map.putIfAbsent("1", "1_2");//如果不存在存入
        map.putIfAbsent("1", "1");
        System.out.println(map.get("1"));
        System.out.println("return:" + s);

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
        System.out.println("================computeIfAbsent=================");
        HashMap<String, Integer> map3 = new HashMap<>();
        Integer integer = map3.computeIfAbsent("1", key -> 1);
        System.out.println(integer);

        System.out.println("================computeIfAbsent4=================");
        HashMap<Integer, Integer> map4 = new HashMap<>();
        map4.computeIfAbsent(1, key -> 1);
        map4.compute(1, new BiFunction<Integer, Integer, Integer>() {
            @Override
            public Integer apply(Integer integer, Integer integer2) {
                return integer + integer2;
            }
        });
        System.out.println(map4.get(1));

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

        System.out.println("elements in HashMap");
        HashMap<String, String> hashMap = new HashMap<>();//元素排序时根据 key的hash值进行的排序
        hashMap.put("1", "1");
        hashMap.put("4", "4");
        hashMap.put("3", "3");
        hashMap.put("2", "2");

        Set<Map.Entry<String, String>> entries = hashMap.entrySet();
        for (Map.Entry<String, String> entry : entries) {
            System.out.println(entry.getKey() + " " + entry.getValue());
        }


        System.out.println("================putnull=================");
        HashMap<Object, Object> testNull = new HashMap<>();
        testNull.put(null, null);
        System.out.println(testNull.get(null));

    }
}
