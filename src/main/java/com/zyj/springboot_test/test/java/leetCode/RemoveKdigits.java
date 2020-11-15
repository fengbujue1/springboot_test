package com.zyj.springboot_test.test.java.leetCode;

import com.sun.jmx.remote.internal.ArrayQueue;

import java.util.Deque;
import java.util.LinkedList;
import java.util.Queue;

public class RemoveKdigits {
    /**
     * 402. 移掉K位数字(使剩下的数字最小)
     * 给定一个以字符串表示的非负整数 num，移除这个数中的 k 位数字，使得剩下的数字最小。
     *
     * 注意:
     *
     * num 的长度小于 10002 且 ≥ k。
     * num 不会包含任何前导零。
     * 示例 1 :
     *
     * 输入: num = "1432219", k = 3
     * 输出: "1219"
     * 解释: 移除掉三个数字 4, 3, 和 2 形成一个新的最小的数字 1219。
     * 示例 2 :
     *
     * 输入: num = "10200", k = 1
     * 输出: "200"
     * 解释: 移掉首位的 1 剩下的数字为 200. 注意输出不能有任何前导零。
     * 示例 3 :
     *
     * 输入: num = "10", k = 2
     * 输出: "0"
     * 解释: 从原数字移除所有的数字，剩余为空就是0。
     *
     */
    public static void main(String[] args) {
//        String num = "1432219";
//        int k = 3;
        //1219

//        String num = "10200";
//        int k = 1;
        //200

        String num = "1234567890";
        int k = 9;

        RemoveKdigits removeKdigits = new RemoveKdigits();
        System.out.println(removeKdigits.removeKdigits(num, k));

    }
    public String removeKdigits(String num, int k) {
        Deque<Integer> deque = new LinkedList<>();
        char[] chars = num.toCharArray();
        for (int i = 0; i < chars.length; i++) {
            Integer integer = Integer.valueOf(String.valueOf(chars[i]));
            while (deque.peekLast() != null && k>0 && integer < deque.peekLast()) {
                k--;
                deque.pollLast();
            }
            deque.offer(integer);
        }
        for (int i = 0; i < k; i++) {
            deque.pollLast();
        }
        if (deque.size() <= 0) {
            return "0";
        }
        StringBuilder stringBuilder = new StringBuilder();
        Integer temp;
        boolean isFirst = true;
        while ((temp = deque.pollFirst()) != null) {
            if (temp != 0) {
                stringBuilder.append(temp);
                isFirst = false;
            } else {
                if (!isFirst) {
                    stringBuilder.append(temp);
                }
            }
        }

        return stringBuilder.length() == 0 ? "0" : stringBuilder.toString();
    }
}
