package com.zyj.springboot_test.test.java.leetCode;

public class MaxScore {
    /**
     * 1423. 可获得的最大点数
     * 几张卡牌 排成一行，每张卡牌都有一个对应的点数。点数由整数数组 cardPoints 给出。
     * 每次行动，你可以从行的开头或者末尾拿一张卡牌，最终你必须正好拿 k 张卡牌。
     * 你的点数就是你拿到手中的所有卡牌的点数之和。
     * 给你一个整数数组 cardPoints 和整数 k，请你返回可以获得的最大点数。
     */

    public static void main(String[] args) {
        int[] cardPoints = {1, 2, 3, 4, 5, 6, 1};
        int[] cardPoints2 = {9,7,7,9,7,7,9};
        int[] cardPoints3 = {1,79,80,1,1,1,200,1};
        int k = 3;
        int k2 = 7;
        int k3 = 3;
        MaxScore maxScore = new MaxScore();
        int result = maxScore.maxScore2(cardPoints3, k3);
        System.out.println(result);

    }
//    public int maxScore(int[] cardPoints, int k) {
//        int startPoint = k;
//        int route = k;
//        int[][] matrix = new int[k][k];
//        for (int i = 0; i < startPoint; i++) {
//            for (int j = 0; j < route; j++) {
//                matrix[i][j];
//            }
//        }
//
//
//    }

    /**
     * 力扣解法：滑动窗口
     *      1.卡牌连续
     *      2.从头尾拿出最大的牌，就是让剩下的连续的牌最小
     *      3.用一个长度为剩余牌数量的 窗口，在初始牌上滑动。
     *      4.当滑动到底，沿途得出的最小值，则对应拿走牌的最大值（因为牌的总数固定）
     *
     */
    public int maxScore2(int[] cardPoints, int k) {
        int limit = cardPoints.length - k;
        int currentstep = 0;
        int min = 0;
        int val = 0;
        int sum = 0;
        for (int i = 0; i < cardPoints.length; i++) {
            sum += cardPoints[i];
            if (currentstep < limit) {
                currentstep++;
                val += cardPoints[i];
                if (currentstep == limit) {
                    min = val;
                }
                continue;
            }
            val = val - cardPoints[i - currentstep] + cardPoints[i];
            min = Math.min(min, val);
        }
        return sum - min;
    }
}
