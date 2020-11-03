package com.zyj.springboot_test.test.java.leetCode;

public class ValidMountainArray {
    /**
     * 941. 有效的山脉数组
     *
     * 给定一个整数数组 A，如果它是有效的山脉数组就返回 true，否则返回 false。
     *
     * 让我们回顾一下，如果 A 满足下述条件，那么它是一个山脉数组：
     *
     * A.length >= 3
     * 在 0 < i < A.length - 1 条件下，存在 i 使得：
     * A[0] < A[1] < ... A[i-1] < A[i]
     * A[i] > A[i+1] > ... > A[A.length - 1]
     *
     * 来源：力扣（LeetCode）
     * 链接：https://leetcode-cn.com/problems/valid-mountain-array
     * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
     */
    public static void main(String[] args) {
        int[] falseDemod1 = {2, 1};
        int[] falseDemod2 = {3,5,5};
        int[] falseDemod3 = {14,82,89,84,79,70,70,68,67,66,63,60,58,54,44,43,32,28,26,25,22,15,13,12,10,8,7,5,4,3};
        int[] trueDemod1 = {0,3,2,1};
        System.out.println(validMountainArray(falseDemod1));
        System.out.println(validMountainArray(falseDemod2));
        System.out.println(validMountainArray(falseDemod3));
        System.out.println(validMountainArray(trueDemod1));

    }

    public static boolean validMountainArray(int[] A) {
        if (A.length < 3) {
            return false;
        }
        boolean result = false;

        if (A[0] > A[1] || A[A.length - 2] < A[A.length - 1]) {
            return false;
        }

        for (int i = 1; i < A.length - 1; i++) {
            if (A[i - 1] < A[i] && A[i] > A[i + 1]) {
                if (!result) {
                    result = true;
                } else {
                    return false;
                }
            }
            if (A[i - 1] == A[i] || A[i] == A[i + 1]) {
                return false;
            }
        }
        return result;
    }
}
