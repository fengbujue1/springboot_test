package com.zyj.springboot_test.test.java.testThread.concurrentCollection;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentSkipListMap;
import java.util.function.BiFunction;

public class TestMap {
    public static void main(String[] args) {
//        testHashMap();

//        testLinkedHashMap();

        SortedMap();
    }

    public static void testHashMap() {
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
    }

    public static void testLinkedHashMap() {
        LinkedHashMap<String, String> linkedHashMap = new LinkedHashMap<>();//元素排序时根据插入时顺序进行的
        linkedHashMap.put("1", "1");
        linkedHashMap.put("4", "4");
        linkedHashMap.put("3", "3");
        linkedHashMap.put("2", "2");

        System.out.println("elements in linkedHashMap");
        Set<Map.Entry<String, String>> entries = linkedHashMap.entrySet();
        for (Map.Entry<String, String> entry : entries) {
            System.out.println(entry.getKey() + " " + entry.getValue());
        }
    }

    public static void testConcurrentHashMap() {//可以用作并发状态下的缓存  MapCacheDemo
        ConcurrentHashMap<String, String> concurrentHashMap = new ConcurrentHashMap<>();
    }

    public static void SortedMap() {// TreeMap 构造器中传入比较器，可以指定排序规则(以key进行比较)
        //使用场景  分布式下的负载均衡的一致性hash算法  TreeMapUseage包

        SortedMap<String, Integer> treeMap = new TreeMap<>(new Comparator<String>() {
            @Override
            public int compare(String key1, String key2) {
                return key1.compareTo(key2);
            }
        });
        System.out.println("elements in SortedMap");
        treeMap.put("a", 1);
        treeMap.put("c", 1);
        treeMap.put("ab", 1);
        treeMap.put("ac", 1);
        treeMap.put("ba", 1);
        treeMap.put("bc", 1);
        treeMap.put("bb", 1);
        treeMap.forEach((key,v)->{
            System.out.println(key);
        });

        System.out.println("sub elements in SortedMap");
        treeMap.subMap("ba", "bc").forEach((key,value)->{
            System.out.println(key);
        });


        System.out.println("tail elements in SortedMap");
        treeMap.tailMap("ba").forEach((key,value)->{
            System.out.println(key);
        });

        System.out.println("head elements in SortedMap");
        treeMap.headMap("ac").forEach((key,value)->{
            System.out.println(key);
        });
    }

    public static void ConcurrentSkipListMap() {// 实现自  ConcurrentNavigableMap接口，和 AbstractMap
        ConcurrentSkipListMap<String, String> concurrentSkipListMap = new ConcurrentSkipListMap<>();

    }
}
