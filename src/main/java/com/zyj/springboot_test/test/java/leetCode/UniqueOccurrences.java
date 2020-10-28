package com.zyj.springboot_test.test.java.leetCode;

import java.util.*;

public class UniqueOccurrences {
    /**
     * 给你一个整数数组 arr，请你帮忙统计数组中每个数的出现次数。
     *
     * 如果每个数的出现次数都是独一无二的，就返回 true；否则返回 false。
     *
     * @param args
     */
    public static void main(String[] args) {
        int[] arr = {1,2};
        System.out.println(uniqueOccurrences(arr));

        HashMap<Integer, Integer> map = new HashMap<>();
        map.put(500, 20);
        map.compute(500,(k,v) -> {
            return v+20;
        });
        System.out.println(map.get(500));

    }
    public static boolean uniqueOccurrences(int[] arr) {
        HashMap<Integer, Integer> map = new HashMap<>();
        for (int i = 0; i < arr.length; i++) {
            if (!map.containsKey(arr[i])) {
                map.put(arr[i], 0);
            } else {
                map.compute(arr[i], (k, v) -> {
                    return v + 1;
                });
            }
        }
        HashSet<Integer> set = new HashSet<>();
        Set<Map.Entry<Integer, Integer>> entries = map.entrySet();
        for (Map.Entry<Integer, Integer> entry : entries) {
            if (!set.contains(entry.getValue())) {
                set.add(entry.getValue());
            } else {
                return false;
            }
        }
        return true;
    }

}
