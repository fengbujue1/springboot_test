package com.zyj.springboot_test.test.java.leetCode;

import java.util.*;

public class RelativeSortArray {
    /**
     * 1122. 数组的相对排序
     *
     * 给你两个数组，arr1 和 arr2，
     *
     * arr2 中的元素各不相同
     * arr2 中的每个元素都出现在 arr1 中
     * 对 arr1 中的元素进行排序，使 arr1 中项的相对顺序和 arr2 中的相对顺序相同。未在 arr2 中出现过的元素需要按照升序放在 arr1 的末尾。
     *
     * 示例：
     *
     * 输入：arr1 = [2,3,1,3,2,4,6,7,9,2,19], arr2 = [2,1,4,3,9,6]
     * 输出：[2,2,2,1,4,3,3,9,6,7,19]
     *
     * [28,6,22,8,44,17]
     * [22,28,8,6]
     *
     * [22,28,8,6,17,44]
     *
     * [2,21,43,38,0,42,33,7,24,13,12,27,12,24,5,23,29,48,30,31]
     * [2,42,38,0,43,21]
     * [2,42,38,0,43,21,5,7,12,12,13,23,24,24,27,29,30,31,33,48]
     */

    public static void main(String[] args) {
        RelativeSortArray relativeSortArray = new RelativeSortArray();
        int[] arr1 = {2, 3, 1, 3, 2, 4, 6, 7, 9, 2, 19};
        int[] arr2 = {2, 1, 4, 3, 9, 6};
        int[] arr1_1 = {2,21,43,38,0,42,33,7,24,13,12,27,12,24,5,23,29,48,30,31};
        int[] arr2_1 = {2,42,38,0,43,21};
        int[] result = relativeSortArray.relativeSortArray(arr1, arr2);

        for (int i = 0; i < result.length; i++) {
            System.out.print(result[i]+" ");
        }

    }

    /**
     * 1.自定义排序算法，
     */
    public int[] relativeSortArray1(int[] arr1, int[] arr2) {
        Map<Integer, Integer> map = new HashMap<>();
        List<Integer> list = new ArrayList<>();
        for(int num : arr1) list.add(num);
        for(int i = 0; i < arr2.length; i++) map.put(arr2[i], i);
        Collections.sort(list, (x, y) -> {
            if(map.containsKey(x) || map.containsKey(y)) return map.getOrDefault(x, 1001) - map.getOrDefault(y, 1001);
            return x - y;
        });
        for(int i = 0; i < arr1.length; i++) arr1[i] = list.get(i);
        return arr1;
    }

    /**
     * 2.计数排序，因为题目中有 所有数字不大于1000这个条件，所以可以用这个方法，操作数组，速度很快
     */
    public int[] relativeSortArray2(int[] arr1, int[] arr2) {
        int upper = 0;
        for (int x : arr1) {
            upper = Math.max(upper, x);
        }
        int[] frequency = new int[upper + 1];
        for (int x : arr1) {
            ++frequency[x];
        }
        int[] ans = new int[arr1.length];
        int index = 0;
        for (int x : arr2) {
            for (int i = 0; i < frequency[x]; ++i) {
                ans[index++] = x;
            }
            frequency[x] = 0;
        }
        for (int x = 0; x <= upper; ++x) {
            for (int i = 0; i < frequency[x]; ++i) {
                ans[index++] = x;
            }
        }
        return ans;
    }

    /**
     * 自实现，不知道写的什么，垃圾的一匹
     */
    public int[] relativeSortArray(int[] arr1, int[] arr2) {
        if (arr1.length == arr2.length) {
            return arr2;
        }
        int[] result = new int[arr1.length];
        ArrayList<Integer> integers2 = new ArrayList<>();
        for (int i = 0; i < arr2.length; i++) {
            integers2.add(arr2[i]);
        }
        int resultIndex = 0;
        int arr2Index = 0;
        int notInIndex = 0;
        boolean first = true;
        ArrayList<Integer> notIn = new ArrayList<>();
        while (arr2Index < arr2.length) {
            for (int i = 0; i < arr1.length; i++) {
                if (arr1[i] == arr2[arr2Index]) {
                    result[resultIndex++] = arr1[i];
                }
                if (first && !integers2.contains(arr1[i])) {
                    notIn.add(arr1[i]) ;
                }
            }
            first = false;
            arr2Index++;
        }
        notIn.sort(new Comparator<Integer>() {
            @Override
            public int compare(Integer o1, Integer o2) {
                return o1 - o2;
            }
        });
        for (int i = 0; i < notIn.size(); i++) {
            result[resultIndex++] = notIn.get(i);
        }
        return result;
    }
}
