package com.zyj.springboot_test.test.java.testThread.concurrentCollection.map.treeMap;

import java.util.Comparator;
import java.util.SortedMap;
import java.util.TreeMap;

/**
 * TreeMap也是SortedMap接口的实现类
 */
public class TestTreeMap {
    public static void main(String[] args) {
        SortedMap();
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
}
