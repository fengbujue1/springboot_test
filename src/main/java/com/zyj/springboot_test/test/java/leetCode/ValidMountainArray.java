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
        int[] falseDemod4 = {9,8,7,6,5,4,3,2,1,0};
        System.out.println(validMountainArray2(falseDemod1));
        System.out.println(validMountainArray2(falseDemod2));
        System.out.println(validMountainArray2(falseDemod3));
        System.out.println(validMountainArray2(trueDemod1));
        System.out.println(validMountainArray2(falseDemod4));

    }

    //线性扫描解法
    public static boolean validMountainArray2(int[] A) {
        if (A.length < 3) {
            return false;
        }
        int topIndex = -1;
        for (int i = 1; i <= A.length - 1; i++) {
            if (A[i - 1] < A[i]) {
                continue;
            } else if (i != 1) {
                topIndex = i - 1;
                break;
            } else return false;
        }
        if (topIndex == -1 || topIndex >= A.length - 1) {
            return false;
        }
        for (int i = topIndex; i < A.length - 1; i++) {
            if (A[i] > A[i + 1]) {
                continue;
            } else {
                return false;
            }
        }
        return true;

    }

    //比较复杂的解法，其实可以更简单
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
