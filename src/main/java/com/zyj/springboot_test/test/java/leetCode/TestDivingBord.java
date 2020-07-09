package com.zyj.springboot_test.test.java.leetCode;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;

/**
 * 跳水板算法
 *你正在使用一堆木板建造跳水板。有两种类型的木板，其中长度较短的木板长度为shorter，长度较长的木板长度为longer。
 * 你必须正好使用k块木板。编写一个方法，生成跳水板所有可能的长度。
 *0 < shorter <= longer
 * 0 <= k <= 100000
 */

/**
 * 解法：
 * 1.考虑完两个边界条件后，其他长度条件下结果不会相同----不用考虑去重
 * 2.逐步减少短木板的数量，结果就是递增的-----不用排序
 *
 */
public class TestDivingBord {
    public static void main(String[] args) {

    }
    public int[] divingBoard(int shorter, int longer, int k) {

        if (k == 0) {
            return new int[0];
        }
        if (shorter == longer) {
            return new int[]{shorter * k};
        }
        int[] reults = new int[k+1];

        for (int l = 0; l <= k; l++) {
            int sNum = k-l;
            int lNum = l;
            reults[l]=(sNum * shorter + lNum* longer);
        }
        return reults;
    }
}
