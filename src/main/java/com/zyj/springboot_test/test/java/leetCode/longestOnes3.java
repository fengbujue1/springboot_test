package com.zyj.springboot_test.test.java.leetCode;

public class longestOnes3 {
    public static void main(String[] args) {
        int[] ints = {1, 1, 1, 0, 0, 0, 1, 1, 1, 1, 0};
        int k = 2;

        longestOnes3 longestOnes3 = new longestOnes3();
        int result = longestOnes3.longestOnes(ints, k);
        System.out.println(result);

    }
    /**
     * 1004. 最大连续1的个数 III
     *
     * 给定一个由若干 0 和 1 组成的数组 A，我们最多可以将 K 个值从 0 变成 1 。
     *
     * 返回仅包含 1 的最长（连续）子数组的长度。
     *
     *示例 1：
     * 输入：A = [1,1,1,0,0,0,1,1,1,1,0], K = 2
     * 输出：6
     * 解释：
     * [1,1,1,0,0,1,1,1,1,1,1]
     * 粗体数字从 0 翻转到 1，最长的子数组长度为 6。
     */

    public int longestOnes(int[] A, int K) {
        return fill(A, K);
    }

    public int fill(int[] A, int k) {
        int head = 0;
        int tail = 0;
        int currentHead = 0;
        int currentTail = 0;
        int headBlank = 0;
        int tailBlank = 0;
        int currentHeadBlank = 0;
        int currentTailBlank = 0;
        for (int i = 0; i < A.length; i++) {
            if (A[i] == 1 && (i == 0 || A[i - 1] == 0)) {
                currentHead = i;
            }
            if (A[i] == 0 && (i == 0 || A[i - 1] == 1)) {
                currentHeadBlank = i;
            }
            if (A[i] == 1 && (i == A.length-1 || A[i + 1] == 0)) {
                currentTail = i;
            }
            if (A[i] == 0 && (i == 0 || A[i - 1] == 1)) {
                currentTailBlank = i;
            }
            if (calcLength(currentHead, currentTail) > calcLength(head, tail)) {
                tail=currentTail;
                head = currentHead;
            }
        }
        return calcLength(head, tail);

    }

    public int calcLength(int head, int tail) {
        if (head != 0 && tail >= head) {
            return tail - head + 1;
        } else {
            return 0;
        }
    }
}
