package com.zyj.springboot_test.test.java.leetCode;

public class MaxNumber {

    /**
     * 321. 拼接最大数
     * 给定长度分别为 m 和 n 的两个数组，其元素由 0-9 构成，
     * 表示两个自然数各位上的数字。
     * 现在从这两个数组中选出 k (k <= m + n) 个数字拼接成一个新的数，
     * 要求从同一个数组中取出的数字保持其在原数组中的相对顺序。
     *
     * 求满足该条件的最大数。结果返回一个表示该最大数的长度为 k 的数组。
     *
     * 说明: 请尽可能地优化你算法的时间和空间复杂度。
     *
     * 示例 1:
     *
     * 输入:
     * nums1 = [3, 4, 6, 5]
     * nums2 = [9, 1, 2, 5, 8, 3]
     * k = 5
     * 输出:
     * [9, 8, 6, 5, 3]
     * 示例 2:
     *
     * 输入:
     * nums1 = [6, 7]
     * nums2 = [6, 0, 4]
     * k = 5
     * 输出:
     * [6, 7, 6, 0, 4]
     * 示例 3:
     *
     * 输入:
     * nums1 = [3, 9]
     * nums2 = [8, 9]
     * k = 3
     * 输出:
     * [9, 8, 9]
     */

    /**
     * 力扣解法：单调栈
     *  //1.计算可能出现的两个数组取值的情况
     *  2.从两个数组中取出指定长度的最大序列，单调栈
     *  3.合并两个序列,因为是单调栈，只需要直接组装即可
     */
    public static void main(String[] args) {
        MaxNumber maxNumber = new MaxNumber();
        int[] nums1 = new int[]{6, 7};
        int[] nums2 = new int[]{6, 0, 4, 5, 6};
        int[] ints = maxNumber.maxNumber(nums1, nums2, 5);
            System.out.println();
        for (int i = 0; i < ints.length; i++) {
            System.out.print(ints[i]);
        }
    }
    public int[] maxNumber(int[] nums1, int[] nums2, int k) {
        int m = nums1.length, n = nums2.length;
        int[] maxSubsequence = new int[k];
        int start = Math.max(0, k - n), end = Math.min(k, m);//1.计算可能出现的两个数组取值的情况
        for (int i = start; i <= end; i++) {
            int[] subsequence1 = maxSubsequence(nums1, i);//2.从两个数组中取出指定长度的最大序列，单调栈
            int[] subsequence2 = maxSubsequence(nums2, k - i);
            int[] curMaxSubsequence = merge(subsequence1, subsequence2);//3.合并两个序列
            if (compare(curMaxSubsequence, 0, maxSubsequence, 0) > 0) {//比较
                System.arraycopy(curMaxSubsequence, 0, maxSubsequence, 0, k);
            }
        }
        return maxSubsequence;
    }

    public int[] maxSubsequence(int[] nums, int k) {
        int length = nums.length;
        int[] stack = new int[k];
        int top = -1;
        int remain = length - k;
        for (int i = 0; i < length; i++) {
            int num = nums[i];
            while (top >= 0 && stack[top] < num && remain > 0) {
                top--;
                remain--;
            }
            if (top < k - 1) {
                stack[++top] = num;
            } else {
                remain--;
            }
        }
        return stack;
    }

    public int[] merge(int[] subsequence1, int[] subsequence2) {
        int x = subsequence1.length, y = subsequence2.length;
        if (x == 0) {
            return subsequence2;
        }
        if (y == 0) {
            return subsequence1;
        }
        int mergeLength = x + y;
        int[] merged = new int[mergeLength];
        int index1 = 0, index2 = 0;
        for (int i = 0; i < mergeLength; i++) {
            if (compare(subsequence1, index1, subsequence2, index2) > 0) {
                merged[i] = subsequence1[index1++];
            } else {
                merged[i] = subsequence2[index2++];
            }
        }
        return merged;
    }

    public int compare(int[] subsequence1, int index1, int[] subsequence2, int index2) {
        int x = subsequence1.length, y = subsequence2.length;
        while (index1 < x && index2 < y) {
            int difference = subsequence1[index1] - subsequence2[index2];
            if (difference != 0) {
                return difference;
            }
            index1++;
            index2++;
        }
        return (x - index1) - (y - index2);
    }

    /**
     * 自己实现一遍leetcode算法
     *      1.计算可能出现的两个数组取值的情况
     *      2.从两个数组中取出指定长度的最大序列，单调栈
     *      3.合并两个序列,因为是单调栈，只需要直接组装即可
     */
    public int[] maxNumberReWrite(int[] nums1, int[] nums2, int k) {
        int length1 = nums1.length;
        int length2 = nums2.length;
        int start = Math.max(0, k - length2);
        int end = Math.min(k, length1);

        return null;
    }
}
