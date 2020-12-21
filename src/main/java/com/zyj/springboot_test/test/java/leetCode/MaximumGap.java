package com.zyj.springboot_test.test.java.leetCode;

import java.util.Arrays;

public class MaximumGap {
    /**
     *164. 最大间距
     *
     * 给定一个无序的数组，找出数组在排序之后，相邻元素之间最大的差值。
     *
     * 如果数组元素个数小于 2，则返回 0。
     *
     * 示例 1:
     *
     * 输入: [3,6,9,1]
     * 输出: 3
     * 解释: 排序后的数组是 [1,3,6,9], 其中相邻元素 (3,6) 和 (6,9) 之间都存在最大差值 3。
     * 示例 2:
     *
     * 输入: [10]
     * 输出: 0
     * 解释: 数组元素个数小于 2，因此返回 0。
     * 说明:
     *
     * 你可以假设数组中所有元素都是非负整数，且数值在 32 位有符号整数范围内。
     * 请尝试在线性时间复杂度和空间复杂度的条件下解决此问题。
     *
     */
    public static void main(String[] args) {
        int[] ints = {3, 6, 9, 1};

        MaximumGap maximumGap = new MaximumGap();
        System.out.println(maximumGap.maximumGap(ints));;
    }


    /**
     * leetcode 题解1：基数排序算法
     * @param nums
     * @return
     */
//    public int maximumGap1(int[] nums) {
//
//    }


    /**
     * 自实现算法：
     *   排序遍历
     * @param nums
     * @return
     */
    public int maximumGap(int[] nums) {
        if (nums.length < 2) {
            return 0;
        }
        Arrays.sort(nums);
        int result = 0;
        for (int i = 0; i < nums.length - 1; i++) {
            result = Math.abs(nums[i] - nums[i + 1]) > result ? Math.abs(nums[i] - nums[i + 1]) : result;
        }
        return result;
    }
}
