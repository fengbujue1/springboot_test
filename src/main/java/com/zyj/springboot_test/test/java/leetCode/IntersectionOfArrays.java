package com.zyj.springboot_test.test.java.leetCode;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;

public class IntersectionOfArrays {
    /**
     * 给定两个数组，编写一个函数来计算它们的交集。
     * @param args
     */
    public static void main(String[] args) {
        int[] ints = {1, 2, 2, 1};
        int[] ints2 = {2,2};
        int[] intersection = intersection2(ints, ints2);
        for (int i = 0; i < intersection.length; i++) {
            System.out.println(intersection[i]);
        }
    }

    //1.双指针算法
    public static int[] intersection2(int[] nums1, int[] nums2) {
        int length1 = nums1.length;
        int length2 = nums2.length;
        int[] result = new int[length1 > length2 ? length2 : length1];
        if (length1 == 0 || length2 == 0) {
            return result;
        }
        Arrays.sort(nums1);
        Arrays.sort(nums2);
        int pre = Integer.MIN_VALUE;//上一个数字
        int resultIndex = 0;//上一个数字
        int index1 = 0;
        int index2 = 0;
        while (index1 < nums1.length && index2 < nums2.length) {
            if (nums1[index1] == nums2[index2] && pre != nums1[index1]) {
                result[resultIndex] = nums1[index1];
                pre = nums1[index1];
                index1++;
                index2++;
                resultIndex++;
            } else {
                if (nums1[index1] > nums2[index2]) {
                    index2++;
                } else {
                    index1++;
                }
            }

        }
        return Arrays.copyOf(result, resultIndex);
    }

    //1.两个集合算法，时间排名只在中游
    public static int[] intersection(int[] nums1, int[] nums2) {
        HashSet<Integer> integers1 = new HashSet<>();
        HashSet<Integer> integers2 = new HashSet<>();
        for (int i = 0; i < nums1.length; i++) {
            integers1.add(nums1[i]);
        }
        for (int i = 0; i < nums2.length; i++) {
            integers2.add(nums2[i]);
        }
        integers1.retainAll(integers2);

        int[] result = new int[integers1.size()];

        Iterator<Integer> iterator = integers1.iterator();

        int index = 0;
        while (iterator.hasNext()) {
            result[index] = iterator.next();
            index++;
        }

        return result;
    }
}
