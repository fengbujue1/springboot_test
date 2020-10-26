package com.zyj.springboot_test.test.java.leetCode;

import java.util.Arrays;
import java.util.Comparator;

public class NumsOfNumLessThanCurtNum {
    /**
     * 给你一个数组 nums，对于其中每个元素 nums[i]，请你统计数组中比它小的所有数字的数目。
     *
     * 换而言之，对于每个 nums[i] 你必须计算出有效的 j 的数量，
     * 其中 j 满足 j != i 且 nums[j] < nums[i] 。
     *
     * 以数组形式返回答案。
     *
     */

    public static void main(String[] args) {
        int[] ints = {8,1,2,2,3};

        int[] result = smallerNumbersThanCurrent2(ints);
        for (int i = 0; i < result.length; i++) {
            System.out.println(result[i]);
        }
    }



    /**
     * 快速排序法，时间复杂度 O(NlogN),相较于暴力法，在时间上可以节省时间
     * @param nums
     * @return
     */
    public static int[] smallerNumbersThanCurrent2(int[] nums) {
        int[] results = new int[nums.length];

        //定义一个二维数组 存储 之前的值，之前的下标
        int[][] temp = new int[nums.length][2];
        for (int i = 0; i < nums.length; i++) {
            temp[i][0] = nums[i];
            temp[i][1] = i;
        }
        //以值对数组的第一个维度进行排序
        Arrays.sort(temp, new Comparator<int[]>() {
            @Override
            public int compare(int[] o1, int[] o2) {
                return o1[0] - o2[0];
            }
        });

        //查找第一个维度，在其左边的数字都是比其小的

//        特殊情况：相同的数字不应该被计入
        int count = -1;
        for (int i = 0; i < nums.length; i++) {
            //只有在遇到第一个不相同的数字的时候，才记录下标，该下标刚好与比它小的数的 数目相同
            if (count == -1 || temp[i][0] != temp[i - 1][0]) {
                count = i;
            }
            results[temp[i][1]] = count;
        }

        return results;
    }

    /**
     * 暴力法，时间复杂度 O(N^2)
     * @param nums
     * @return
     */
    public static int[] smallerNumbersThanCurrent1(int[] nums) {
        int[] results = new int[nums.length];
        for (int i = 0; i < nums.length; i++) {
            for (int j = 0; j < nums.length; j++) {
                if (j == i) {
                    continue;
                }
                if (nums[j] < nums[i]) {
                    results[i] += 1;
                }
            }
        }
        return results;
    }
}
