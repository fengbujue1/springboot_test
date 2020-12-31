package com.zyj.springboot_test.test.java.leetCode;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashSet;

public class EraseOverlapIntervals {
    /**
     *435. 无重叠区间
     *
     * 给定一个区间的集合，找到需要移除区间的最小数量，使剩余区间互不重叠。
     *
     * 注意:
     *
     * 可以认为区间的终点总是大于它的起点。
     * 区间 [1,2] 和 [2,3] 的边界相互“接触”，但没有相互重叠。
     * 示例 1:
     *
     * 输入: [ [1,2], [2,3], [3,4], [1,3] ]
     *
     * 输出: 1
     *
     * 解释: 移除 [1,3] 后，剩下的区间没有重叠。
     * 示例 2:
     *
     * 输入: [ [1,2], [1,2], [1,2] ]
     *
     * 输出: 2
     *
     * 解释: 你需要移除两个 [1,2] 来使剩下的区间没有重叠。
     * 示例 3:
     *
     * 输入: [ [1,2], [2,3] ]
     *
     * 输出: 0
     *
     * 解释: 你不需要移除任何区间，因为它们已经是无重叠的了。
     *
     */

    public static void main(String[] args) {
        EraseOverlapIntervals eraseOverlapIntervals = new EraseOverlapIntervals();
//        int[][] intervals = {{1, 2}, {1, 2}, {1, 2}};
//        int[][] intervals = {{1, 2}, {2, 3}};
//        int[][] intervals = {{1, 2}, {2, 3}, {3, 4}, {1, 3}};
        int[][] intervals = {{1, 100}, {11, 22}, {1, 11}, {2, 12}};
        System.out.println(eraseOverlapIntervals.eraseOverlapIntervals3(intervals));
    }

    /**
     * 贪心算法
     * <p>
     * 1.以结束节点 升序排序，
     * 2.以第一个结束节点的尾部作为起点，剔除重合区间
     * 3.找到下一个不重合的节点（局部最优）
     * 4.以第二个不重合节点的尾部为起点
     * 5.直到结束即为最优解
     */
    public int eraseOverlapIntervals3(int[][] intervals) {
        int n = intervals.length;
        if (n <= 0) {
            return 0;
        }
        Arrays.sort(intervals, new Comparator<int[]>() {
            @Override
            public int compare(int[] o1, int[] o2) {
                return o1[1] - o2[1];
            }
        });

        int cout = 1;//最少都有一个
        int end = intervals[0][1];//以第一个区间的结束点为起点

        for (int i = 1; i < intervals.length; i++) {
            if (intervals[i][0] >= end) {
                end = intervals[i][1];
                cout++;
            }
        }
        return n-cout;
    }

    /**
     * 动态规划算法
     *
     * 先反向解释本题：找出给定区间最多的不重叠区间
     *
     * 1.先按照头结点排序（或者尾节点），排序后就有了规律
     * 2.依次遍历每个区间（本），将其与“前面的”区间（样）做比较，是否有重叠
     *      不重叠的话：
     *          取 本 和 样  之间的最大的那个
     */
    public int eraseOverlapIntervals2(int[][] intervals) {
        if (intervals.length <= 0) {
            return 0;
        }
        int n = intervals.length;
        Arrays.sort(intervals, new Comparator<int[]>() {
            @Override
            public int compare(int[] o1, int[] o2) {
                return o1[0] - o2[0];
            }
        });

        int[] f = new int[n];
        Arrays.fill(f, 1);

        for (int i = 1; i < n; i++) {
            for (int j = 0; j < i; j++) {
                if (intervals[j][1] <= intervals[i][0]) {
                    f[i] = Math.max(f[i], f[j] + 1);
                }
            }
        }
        return n - Arrays.stream(f).max().getAsInt();
    }

    /**
     * 算法有缺陷，不往下钻了，没有结果
     *
     *  自实现算法：
     *      0.不考虑边界条件相等，因为元素每个区的起点和终点不会是同一个
     *      1.多次遍历，
     *      2.遇到相交集的元素，移除长度最长的那个
     *      3.考虑重叠的定义: intervals[i][0]<intervals[j][1](相等不是重叠，只是接触，可以允许)
     */
    public int eraseOverlapIntervals(int[][] intervals) {
        boolean stop = false;
        HashSet<Integer> removeIndex = new HashSet<>();
//        while (!stop) {
            for (int i = 0; i < intervals.length; i++) {
                if (!removeIndex.contains(i)) {
                    for (int j = i + 1; j < intervals.length; j++) {
                        if (!removeIndex.contains(j)) {
                            if (
                                    (intervals[i][0] <= intervals[j][0] &&
                                            intervals[i][1] > intervals[j][0])||
                                            (intervals[i][0] < intervals[j][1] &&
                                                    intervals[i][1] >= intervals[j][1])
                            ) {
                                if (intervals[j][1] - intervals[j][0] > intervals[i][1] - intervals[i][0]) {
                                    removeIndex.add(j);
                                } else {
                                    removeIndex.add(i);
                                }
                            }
                        }
                    }
                }

            }
//        }
        return removeIndex.size();
    }
}
