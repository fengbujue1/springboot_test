package com.zyj.springboot_test.test.java.leetCode;

import java.util.Comparator;
import java.util.PriorityQueue;

public class ReorganizeString {
    /**
     * 767. 重构字符串
     *
     * 给定一个字符串S，检查是否能重新排布其中的字母，使得两相邻的字符不同。
     *
     * 若可行，输出任意可行的结果。若不可行，返回空字符串。
     *
     * 示例 1:
     *
     * 输入: S = "aab"
     * 输出: "aba"
     * 示例 2:
     *
     * 输入: S = "aaab"
     * 输出: ""
     * 注意:
     *
     * S 只包含小写字母并且长度在[1, 500]区间内。
     */

    public static void main(String[] args) {
        ReorganizeString reorganizeString = new ReorganizeString();
        String S = "aab";
//        String S = "aaab";
        System.out.println(reorganizeString.reorganizeString(S));
    }

    public String reorganizeString(String S) {
        if (S.length() < 2) {
            return S;
        }
        int[] counts = new int[26];
        int maxCount = 0;
        for (int i = 0; i < S.length(); i++) {
            char c = S.charAt(i);
            counts[c - 'a']++;
            maxCount = Math.max(maxCount, counts[c - 'a']);
        }
        if (maxCount > (S.length() + 1) / 2) {
            return "";
        }
        PriorityQueue<Character> queue = new PriorityQueue<>(new Comparator<Character>() {
            @Override
            public int compare(Character char1, Character char2) {
                return counts[char2 - 'a'] - counts[char1 - 'a'];
            }
        });
        for (char i = 'a'; i <= 'z'; i++) {
            if (counts[i - 'a'] > 0) {
                char c = i;
                queue.offer(c);
            }
        }
        StringBuilder sb = new StringBuilder();

        while (queue.size() > 1) {
            Character c1 = queue.poll();
            Character c2 = queue.poll();
            sb.append(c1);
            sb.append(c2);
            counts[c1 - 'a']--;
            counts[c2 - 'a']--;

            if (counts[c1 - 'a'] > 0) {
                queue.offer(c1);
            }
            if (counts[c2 - 'a'] > 0) {
                queue.offer(c2);
            }

        }
        if (queue.size() == 1) {
            sb.append(queue.poll());
        }
        return sb.toString();
    }
}
