package com.zyj.springboot_test.test.java.leetCode;

import java.util.Arrays;

public class Candy {
    /**
     * 老师想给孩子们分发糖果，有 N 个孩子站成了一条直线，老师会根据每个孩子的表现，
     * 预先给他们评分。
     * <p>
     * 你需要按照以下要求，帮助老师给这些孩子分发糖果：
     * <p>
     * 每个孩子至少分配到 1 个糖果。
     * 相邻的孩子中，评分高的孩子必须获得更多的糖果。
     * 那么这样下来，老师至少需要准备多少颗糖果呢？
     * <p>
     * 输入: [1,0,2]
     * 输出: 5
     * 解释: 你可以分别给这三个孩子分发 2、1、2 颗糖果。
     * <p>
     * 输入: [1,2,2]
     * 输出: 4
     * 解释: 你可以分别给这三个孩子分发 1、2、1 颗糖果。
     * 第三个孩子只得到 1 颗糖果，这已满足上述两个条件。
     */

    public static void main(String[] args) {
        int[] ratings  = {1, 0, 2};
        int[] ratings2 = {1, 2, 2};
        int[] ratings3 = {1, 2, 87, 87, 87, 2, 1};
        int[] ratings4 = {1,6,10,8,7,3,2};
        Candy candy = new Candy();
        System.out.println(candy.candy2(ratings4));
    }

    /**
     * 自实现，贪心算法，耗时严重超标
     */
    public int candy(int[] ratings) {
        int[] count = new int[ratings.length];
        Arrays.fill(count, 1);
        int index = 0;
        while (index < ratings.length - 1) {
            if (ratings[index] > ratings[index + 1] && count[index] <= count[index + 1]) {
                count[index]++;
                if (index > 0) {
                    index--;
                    continue;
                }
            } else if (ratings[index] < ratings[index + 1]&&count[index]>=count[index+1]) {
                count[index + 1] = count[index] + 1;
            }
            index++;
        }
        int sum = 0;
        for (int i = 0; i < count.length; i++) {
            sum += count[i];
        }
        return sum;
    }
    /**
     * leetcode解法，两次遍历
     * 分别套用左右规则计算每个小孩该得的糖果，
     * 最终结果取 较大的那个，即为同时满足左右规则的数量
     */
    public int candy2(int[] ratings) {
        int[] left = new int[ratings.length];
        int[] right = new int[ratings.length];
        int sum = 0;
        //右规则计算
        for (int i = 0; i < ratings.length-1; i++) {
            if (ratings[i] < ratings[i + 1] && i > 0) {
                left[i + 1] = left[i] + 1;
            } else {
                left[i + 1] = 1;
            }
        }
        //右规则计算
        for (int i = ratings.length-1; i > 0; i--) {
            if (ratings[i - 1] > ratings[i] && i < ratings.length - 1) {
                right[i - 1] = right[i] + 1;
            } else {
                right[i] = 1;
            }
            sum += Math.max(left[i], right[i]);
        }
        sum += Math.max(left[ratings.length-1], right[ratings.length-1]);
        return sum;
    }
}
