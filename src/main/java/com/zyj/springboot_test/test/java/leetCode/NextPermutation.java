package com.zyj.springboot_test.test.java.leetCode;

public class NextPermutation {
    /**
     * 31. 下一个排列
     *
     * 实现获取下一个排列的函数，算法需要将给定数字序列重新排列成字典序中下一个更大的排列。
     *
     * 如果不存在下一个更大的排列，则将数字重新排列成最小的排列（即升序排列）。
     *
     * 必须原地修改，只允许使用额外常数空间。
     *
     * 以下是一些例子，输入位于左侧列，其相应输出位于右侧列。
     * 1,2,3 → 1,3,2
     * 3,2,1 → 1,2,3
     * 1,1,5 → 1,5,1
     *
     */

    public static void main(String[] args) {

        NextPermutation nextPermutation = new NextPermutation();
        int[] test1 = {1, 2, 3};
        int[] test2 = {3, 2, 1};
        int[] test3 = {1, 1, 5};

        nextPermutation.nextPermutation(test1);
        for (int i = 0; i < test1.length; i++) {
            System.out.print(test1[i]);
        }
        System.out.println("");

        nextPermutation.nextPermutation(test2);
        for (int i = 0; i < test2.length; i++) {
            System.out.print(test2[i]);
        }
        System.out.println("");

        nextPermutation.nextPermutation(test3);
        for (int i = 0; i < test3.length; i++) {
            System.out.print(test3[i]);
        }
        System.out.println("");

    }

    /**
     * 解法：
     *  1.从后往前找 第一次出现 后面数比前面大的情况，证明“前面的”这个数可以被替换
     *  2.再从后往前找，到“前面的”这个数为止，找到第一次比它大的数
     *  3.替换
     *  4.替换后 “前面的”这个数 之后的数全是递减的，直接给它翻转顺序即可
     *
     */
    public void nextPermutation(int[] nums) {
        int index = nums.length - 2;
        while (index >= 0 && nums[index] >= nums[index + 1]) {
            index--;
        }
        if (index >= 0) {
            int i = nums.length - 1;
            while (i >= 0 && nums[index] >= nums[i]) {
                i--;
            }
            swap(nums, index, i);
        }
        reverse(nums, index+1);
    }

    public void swap(int[] nums, int a, int b) {
        int temp = nums[a];
        nums[a] = nums[b];
        nums[b] = temp;
    }
    public void reverse(int[] nums, int start) {
        int left = start;
        int right = nums.length - 1;
        while (left < right) {
            swap(nums, left, right);
            left++;
            right--;
        }

    }

}
