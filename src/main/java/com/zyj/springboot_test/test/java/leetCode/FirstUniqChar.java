package com.zyj.springboot_test.test.java.leetCode;

import java.util.Arrays;

public class FirstUniqChar {
    /**
     * 387. 字符串中的第一个唯一字符
     *
     * 给定一个字符串，找到它的第一个不重复的字符，并返回它的索引。如果不存在，则返回 -1。
     *
     */
    public static void main(String[] args) {
        FirstUniqChar firstUniqChar = new FirstUniqChar();
        String s = "leetcode";
        String s2 = "loveleetcode";
        System.out.println(firstUniqChar.firstUniqChar(s2));
    }
    public int firstUniqChar(String s) {
        char[] chars = s.toCharArray();
        int[] counts = new int[26];
        char[] relations = new char[26];
        Arrays.fill(relations, '1');
        for (int i = 0; i < chars.length; i++) {
            for (int j = 0; j < relations.length; j++) {
                if (relations[j] == '1') {
                    relations[j] = chars[i];
                    counts[j] = 1;
                    break;
                } else if (relations[j] == chars[i]) {
                    counts[j]++;
                    break;
                }
            }
        }
        for (int i = 0; i < counts.length; i++) {
            if (counts[i] == 1) {
                for (int j = 0; j < chars.length; j++) {
                    if (relations[i] == chars[j]) {
                        return j;
                    }
                }
            }
        }
        return -1;

    }
}
