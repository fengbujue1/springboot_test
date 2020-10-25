package com.zyj.springboot_test.test.java.leetCode;

import java.util.ArrayList;

public class LongestMountains {
    /**
     *
     * 845. 数组中的最长山脉
     * 我们把数组 A 中符合下列属性的任意连续子数组 B 称为 “山脉”：
     *
     * B.length >= 3
     * 存在 0 < i < B.length - 1 使得 B[0] < B[1] < ... B[i-1] < B[i] > B[i+1] > ... > B[B.length - 1]
     * （注意：B 可以是 A 的任意子数组，包括整个数组 A。）
     *
     * 给出一个整数数组 A，返回最长 “山脉” 的长度。
     *
     * 如果不含有 “山脉” 则返回 0。
     *
     */

    public static void main(String[] args) {
        int[] A=new int[]{0,1,2,3,4,5,4,3,2,1,0};
        System.out.println(longestMountain(A));

    }

    /**
     * 找出峰尖，然后向左右查找最大距离。取最长的距离
     *  1.因为每个"山脉"左边严格递增，右边严格递减
     *
     *  在同一轮遍历中就可以 找出每个节点，左边的最大拓展数 和 右边的最大拓展数
     *
     *  第二次遍历 每个下标 ，计算出最大的可拓展数，即为结果：最长山脉
     *
     * @param A
     * @return
     */
    public static int longestMountain(int[] A) {
        if (A.length < 3) {
            return 0;
        }
        int[] lefts = new int[A.length];
        int[] rights = new int[A.length];


        //找出每个下边向左右的最大可拓展数
        for (int i = 1; i < A.length; i++) {
            lefts[i] = A[i - 1] < A[i] ? lefts[i - 1] + 1 : 0;
        }
        for (int i = A.length - 2; i > 0; i--) {
            rights[i] = A[i] > A[i+1] ? rights[i+1] + 1 : 0;
        }

        int maxLength = 0;
        for (int i = 0; i < A.length; i++) {
            if (lefts[i] > 0 && rights[i] > 0) {

                maxLength = Math.max(lefts[i] + rights[i] + 1, maxLength);
            }
        }




//        //找出峰尖
//        ArrayList<Integer> tops = new ArrayList<>();
//        for (int i = 1; i < A.length; i++) {
//            if (i == A.length - 1) {
//                break;
//            }
//            if (A[i - 1] < A[i] && A[i] > A[i + 1]) {
//                tops.add(i);
//            }
//
//        }
//
//        //计算各个峰尖代表的山脉的长度,找出最长的
//        int maxLength = 0;
//        for (int top : tops) {
//            int result = calc(A, top);
//            if (result> maxLength) {
//                maxLength = result;
//            }
//        }
        return maxLength;

    }

    public static int calc(int[] A, int top) {
        int left = top;
        int leftLength = 0;
        int right = top;
        int rightLength = 0;
        //向左边开始找
        while (left > 0 && A[left - 1] < A[left]) {
            left -= 1;
            leftLength++;
        }
        //向右边开始找
        while (right < A.length-1 && A[right + 1] < A[right]) {
            right += 1;
            rightLength++;
        }
        return rightLength + leftLength + 1;
    }
}
