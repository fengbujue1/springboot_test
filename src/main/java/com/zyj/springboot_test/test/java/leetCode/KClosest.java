package com.zyj.springboot_test.test.java.leetCode;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

public class KClosest {
    /**
     * 973. 最接近原点的 K 个点
     *
     * 我们有一个由平面上的点组成的列表 points。需要从中找出 K 个距离原点 (0, 0) 最近的点。
     *
     * （这里，平面上两点之间的距离是欧几里德距离。）
     *
     * 你可以按任何顺序返回答案。除了点坐标的顺序之外，答案确保是唯一的。
     */

    public static void main(String[] args) {
        int[][] points = {{3, 3}, {5, -1}, {-2, 4}};

        int k = 2;
        int[][] ints = new KClosest().kClosest(points, k);
        System.out.println(1);

    }
    public int[][] kClosest(int[][] points, int K) {
        ArrayList<int[]> temps = new ArrayList<>();
        temps.addAll(Arrays.asList(points));
        temps.sort(new Comparator<int[]>() {
            @Override
            public int compare(int[] o1, int[] o2) {
                if (Math.pow(o1[0], 2) + Math.pow(o1[1], 2) > (Math.pow(o2[0], 2) + Math.pow(o2[1], 2))) {
                    return 1;
                } else if (Math.pow(o1[0], 2) + Math.pow(o1[1], 2) < (Math.pow(o2[0], 2) + Math.pow(o2[1], 2))) {
                    return -1;
                } else {
                    return 0;
                }
            }
        });
        int[][] result = new int[K][2];
        for (int i = 0; i < K; i++) {
            result[i] = temps.get(i);
        }

        return result;
    }
}
