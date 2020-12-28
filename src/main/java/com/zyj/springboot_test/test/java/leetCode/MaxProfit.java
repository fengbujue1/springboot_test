package com.zyj.springboot_test.test.java.leetCode;

import java.util.Arrays;

public class MaxProfit {
    /**
     * 188. 买卖股票的最佳时机 IV
     *
     * 给定一个整数数组 prices ，它的第 i 个元素 prices[i] 是一支给定的股票在第 i 天的价格。
     *
     * 设计一个算法来计算你所能获取的最大利润。你最多可以完成 k 笔交易。
     *
     * 注意：你不能同时参与多笔交易（你必须在再次购买前出售掉之前的股票）。
     *
     * 示例 1：
     *
     * 输入：k = 2, prices = [2,4,1]
     * 输出：2
     * 解释：在第 1 天 (股票价格 = 2) 的时候买入，在第 2 天 (股票价格 = 4) 的时候卖出，
     * 这笔交易所能获得利润 = 4-2 = 2 。
     *
     * 示例 2：
     *
     * 输入：k = 2, prices = [3,2,6,5,0,3]
     * 输出：7
     * 解释：在第 2 天 (股票价格 = 2) 的时候买入，在第 3 天 (股票价格 = 6) 的时候卖出,
     * 这笔交易所能获得利润 = 6-2 = 4 。
     * 随后，在第 5 天 (股票价格 = 0) 的时候买入，在第 6 天 (股票价格 = 3) 的时候卖出,
     * 这笔交易所能获得利润 = 3-0 = 3
     */


    public static void main(String[] args) {
        MaxProfit maxProfit = new MaxProfit();
        int[] prices = {3, 2, 6, 5, 0, 3};
        int k = 2;
        System.out.println(maxProfit.maxProfit(k, prices));
    }

    /**
     * 动态规划算法
     */
    public int maxProfit(int k, int[] prices) {
        int n = prices.length;//一共最多n天"次"交易（一笔交易包含 一次买，一次卖，最终结束手上不持有股票）
        if (n == 0) {
            return 0;
        }

        //因为k天只能进行 n/2次交易，所以需要做边界
        k = Math.min(n / 2, k);
        //buy[i][j]表示恰好进行j次交易，当前手上如果持有一支股票的收益
        int[][] buy = new int[n][k+1];//+1是因为buy[0][0]是一个边界条件，实际不存在，
        // 依据算法，第一天需要依赖于前一天的交易计算最大收益。
        //sell[i][j]表示恰好进行j次交易，当前手上如果不持有股票的收益
        int[][] sell = new int[n][k+1];


        //第一天其余都是非法节点，设置一个极小值，避免对后续结果发生干扰
        for (int i = 1; i <= k; i++) {
            buy[0][i] = sell[0][i] = Integer.MIN_VALUE/2;//  防止Integer.MIN_VALUE 再作减法运算，得出+的结果
        }
        //边界条件：第一天只有两种状态：买，或者不买
        buy[0][0] = -prices[0];
        sell[0][0] = 0;

        //后续每天的“买”或者“卖”的收益，都依赖于前一天买或者卖的情况
        //前一天“买”，那当天可以选择“不卖”或者“卖”，
        //前一天“卖”，那当天可以选择“买”或者“观望”，
        //两者之间取最大收益作为今天的收益
        //以数组形式，可以计算多“次”交易时候可能的情况（穷举）
        for (int i = 1; i < n; i++) {
            //每一天都可能是第一次买的情况
            //该值需要在  前一天  “是”“否”买了两种情况做比较，
            //前一天买了，今天就是持有（沿用昨天的收益，今天不能再买）
            //前一天没买，今天购买，收益需要减去当天的股价
            //两者之间选一个最大的收益
            buy[i][0] = Math.max(buy[i - 1][0], sell[i - 1][0] - prices[i]);
            for (int j = 1; j <= k; j++) {
                //当天如果手上持有，
                // 1。可能是昨天买的，昨天买的今天就不能买，总收益就是buy[i-1][j]
                // 2。可能是今天买的 总收益就是 sell[i][j]-prices[i]
                //取最大收益？？
                buy[i][j] = Math.max(buy[i - 1][j], sell[i-1][j] - prices[i]);
                //当天如果出售，
                // 1。可能是昨天就持有了，总收益就是 buy[i-1][j-1]+prices[i]
                // 2。可能是今天买的,就只能选择持有 总收益就是 sell[i-1][j]
                //取最大收益？？
                sell[i][j] = Math.max(buy[i-1][j-1] + prices[i], sell[i-1][j]);
            }
        }

        //遍历最后一天的所有 手上不存在股票（sell） 交易次数 ，取其中最大的收益即为最优解
        return Arrays.stream(sell[n - 1]).max().getAsInt();
    }
}
