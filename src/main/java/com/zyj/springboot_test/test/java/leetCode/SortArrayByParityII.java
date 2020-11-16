package com.zyj.springboot_test.test.java.leetCode;

public class SortArrayByParityII {
    /**
     *
     * 922. 按奇偶排序数组 II
     * 给定一个非负整数数组 A， A 中一半整数是奇数，一半整数是偶数。
     *
     * 对数组进行排序，以便当 A[i] 为奇数时，i 也是奇数；当 A[i] 为偶数时， i 也是偶数。
     *
     * 你可以返回任何满足上述条件的数组作为答案。
     *
     */
    public static void main(String[] args) {
    }

    /**
     * 解法，双指针：一个记录奇数，一个记录偶数，新建数组，
     */
    public int[] sortArrayByParityII(int[] A) {
        int unevenIndex = 0;
        int evenIndex = 1;
        int step = 2;
        int[] result = new int[A.length];
        for (int i = 0; i < A.length; i++) {
            if (A[i] % 2 == 1) {
                result[evenIndex] = A[i];
                evenIndex += step;
            } else {
                result[unevenIndex] = A[i];
                unevenIndex += step;
            }
        }
        return result;
    }
}
