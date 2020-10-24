package com.zyj.springboot_test.test.java.designMode.modelMethod;

import java.lang.reflect.Array;
import java.util.Arrays;

public class Usages {
    public static void main(String[] args) {
        Num[] nums = new Num[]{new Num(2),new Num(1),new Num(5),
                new Num(2),new Num(1),new Num(5),
                new Num(2),new Num(1),new Num(5)};
        Arrays.sort(nums);//数组的排序方法使用到了模板方法，算法的子类部分实现就是Comparable接口的实现

        System.out.println(nums);
    }
}
