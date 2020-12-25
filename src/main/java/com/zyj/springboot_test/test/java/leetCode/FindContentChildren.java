package com.zyj.springboot_test.test.java.leetCode;

import java.sql.Array;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;

public class FindContentChildren {
    /**
     * 455. 分发饼干
     * 假设你是一位很棒的家长，想要给你的孩子们一些小饼干。但是，每个孩子最多只能给一块饼干。
     *
     * 对每个孩子 i，都有一个胃口值 g[i]，这是能让孩子们满足胃口的饼干的最小尺寸；并且每块饼干 j，
     * 都有一个尺寸 s[j] 。如果 s[j] >= g[i]，我们可以将这个饼干 j 分配给孩子 i ，这个孩子会得到满足。
     * 你的目标是尽可能满足越多数量的孩子，并输出这个最大数值。
     *
     * 1 <= g.length <= 3 * 104
     * 0 <= s.length <= 3 * 104
     * 1 <= g[i], s[j] <= 231 - 1
     *
     * 输入: g = [1,2,3], s = [1,1]
     * 输出: 1
     *
     * 输入: g = [1,2], s = [1,2,3]
     * 输出: 2
     */


    public static void main(String[] args) {
        int[] g1 = {1, 2, 3};
        int[] s1 = {3};

        int[] g2 = {1,2};
        int[] s2 = {1,2,3};

        FindContentChildren findContentChildren = new FindContentChildren();
        System.out.println(findContentChildren.findContentChildren(g1,s1));
    }

    /**
     * 优化了三次，两个数组全部排序后，只需要遍历一次孩子数组
     * 双下标，如果最大的糖果都不能满足当前孩子，那就跳到下一个
     * 如果当前糖果（最大的糖果）可以满足，则下表刻度都往下移一位
     * 知道孩子遍历完 或者 糖果发完。
     */
    public int findContentChildren(int[] g, int[] s) {
        if (s.length == 0 || g.length == 0) {
            return 0;
        }
        Arrays.sort(g);
        Arrays.sort(s);

        int count = 0;
        int index = s.length-1;
        for (int i = g.length - 1; i >= 0; i--) {
            if (s[index] >= g[i]) {
                s[index] = -1;
                count++;
                index--;
            }
            if (index < 0) {
                break;
            }
        }
        return count;
    }
}
