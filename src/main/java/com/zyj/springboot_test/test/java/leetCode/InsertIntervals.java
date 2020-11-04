package com.zyj.springboot_test.test.java.leetCode;

import com.sun.org.apache.bcel.internal.generic.I2F;
import org.bouncycastle.util.Arrays;

import java.util.ArrayList;

public class InsertIntervals {
    /**
     * 给出一个无重叠的 ，按照区间起始端点排序的区间列表。
     *
     * 在列表中插入一个新的区间，
     * 你需要确保列表中的区间仍然有序且不重叠（如果有必要的话，可以合并区间）。
     *
     *
     * @param args
     */
    public static void main(String[] args) {
        int [][] intervals = {{1,3},{6,9}};
        int[] newInterval = {2, 5};

        int[][] intervals2 = {{1, 2}, {3, 5}, {6, 7}, {8, 10}, {12, 16}};
        int[] newInterval2 = {4,8};

        int[][] intervals3 = {{1, 5}};
        int[] newInterval3 = {1,7};

        int[][] intervals4 = {{1,2},{3,5},{6,7},{8,10},{12,16}};
        int[] newInterval4 = {4,8};
//        [[1,2],[3,10],[12,16]]
        insert(intervals4, newInterval4);
    }

    public static int[][] insert2(int[][] intervals, int[] newInterval) {
        ArrayList<int[]> results = new ArrayList<>();

        int left = newInterval[0];
        int right = newInterval[1];

        boolean inserted = false;

        for (int[] interval : intervals) {
            if (interval[0] > right) {
                //区间的左边界在新区间右边，无交集，可以加入结果集（注意前后顺序）
                if (!inserted) {
                    //因为区间是从左往右递增，遇到的第一个与新区间 的右边不相交的区间的时候，
                    // 说明后面的区间再也不会相交了，此时可以把新区间加入结果集（无论是否有被扩充过）
                    inserted = true;
                    results.add(new int[]{left,right});
                }
                results.add(interval);

            } else if (interval[1] < left) {
                //没有交集，可以加入结果集
                results.add(interval);
            } else {
                //有交集，此时需要对新加入的区间进行整顿
                left = Math.min(left, interval[0]);
                right = Math.max(right, interval[1]);
            }
        }
        if (!inserted) {
            //如果全部遍历结束后，还没有添加新区间，那这个区间是OK的，直接添加
            results.add(new int[]{left, right});
        }

        int[][] ints = new int[results.size()][2];
        for (int i = 0; i < results.size(); i++) {
            ints[i] = results.get(i);
        }
        return ints;

    }

    //自实现算法，算法有漏洞，导致边界条件太多，修修补补N多次，放弃了
    public static int[][] insert(int[][] intervals, int[] newInterval) {
        if (intervals.length == 0) {
            int[][] ints = new int[1][2];
            ints[0] = newInterval;

            return ints;
        }

        int[][] white = intervals;
        int[] mergeInterval = new int[]{Integer.MIN_VALUE,Integer.MIN_VALUE};

        for (int i = 0; i < white.length; i++) {
            //遍历所有区间
            if (white[i][0] <= newInterval[0] && white[i][1] >= newInterval[0]) {
                //新区间的头部落入某个白区间中,以白区间的头为头
                mergeInterval[0] = white[i][0];
            }
            if (i != white.length - 1 && white[i][1] < newInterval[0] && white[i + 1][0] > newInterval[0]) {
                //新区间的头部落入某个黑区间中,以新区间头为头
                mergeInterval[0] = newInterval[0];
            }
            if (white[i][0] <= newInterval[1] && white[i][1] >= newInterval[1]) {
                //新区间的尾部部落入某个白区间中,以白区间的尾为尾
                mergeInterval[1] = white[i][1];
            }
            if (i == white.length - 1 ||(white[i][1] < newInterval[1] && white[i+1][0] > newInterval[1])) {
                //新区间的尾部部落入某个黑区间中,以新区间的尾为尾
                mergeInterval[1] = newInterval[1];
            }
        }

        boolean isExchanged = false;
        int validElements = 0;
        for (int i = 0; i < white.length; i++) {
            //二次比遍历，执行替换
            //遍历所有区间,凡是新区间被包含了，就执行替换为null
//            同时如果存在区间首尾相连，进行合并
            if (white[i] != null && white[i][0] >= mergeInterval[0] && white[i][1] <= mergeInterval[1]) {
                if (!isExchanged) {
                    validElements++;
                    isExchanged = true;
                    white[i] = mergeInterval;
                } else {
                    white[i] = null;
                }
                if (i != white.length - 1 && mergeInterval[1] == white[i + 1][0]) {
                    mergeInterval[1] = white[i + 1][1];
                    white[i + 1] = null;
                    validElements--;
                }
            } else {
                validElements++;
            }
        }

        int[][] results = new int[validElements][2];
        int index = 0;
        for (int i = 0; i < white.length; i++) {
            //三次比遍历，执行拷贝
            if (white[i] != null) {
                results[index] = white[i];
                index++;
            }
        }
        return results;
    }
}
