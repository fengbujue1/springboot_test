package com.zyj.springboot_test.test.java.serialization.google_protobuff.protostuff;

//import com.zyj.springboot_test.test.java.serialization.model.Numb;
import com.zyj.springboot_test.util.SerializeUtil;

import java.util.ArrayList;

public class SerilizeCollection {
    /**
     * 测试 pb  序列化 和反序列化 集合类型
     * @param args
     */
    public static void main(String[] args) {


        //准备大数据
//        ArrayList<Numb> numbs = new ArrayList<>();
//        ArrayList<Integer> Integers = new ArrayList<>();
//        for (int i = 0; i < 10000; i++) {
//            numbs.add(Numb.getInstance());
//        }
//        for (int i = 0; i < 10000; i++) {
//            Integers.add(i);
//        }
//
//        //准备以前次循环的数据
//        ArrayList<ArrayList> lists = new ArrayList<>();
//        for (int i = 0; i < 1000; i++) {
//            lists.add(new ArrayList(numbs));
//        }
//
//        ArrayList<byte[]> serializeResult = new ArrayList<>();
//
//
//        long before1 = System.currentTimeMillis();
//        for (ArrayList list : lists) {
//            serializeResult.add(SerializeUtil.bean2Byte(list, ArrayList.class));
//        }
//        long after1 = System.currentTimeMillis();
//        System.out.println("序列化一千次（存了一万个bean的List）：" + (after1 - before1));
//
//        //准备以前次循环的数据
//        ArrayList<ArrayList> listInt = new ArrayList<>();
//        for (int i = 0; i < 1000; i++) {
//            listInt.add(new ArrayList(Integers));
//        }
//
//        ArrayList<byte[]> serializeResult_Int = new ArrayList<>();
//
//
//        long before2 = System.currentTimeMillis();
//        for (ArrayList list : listInt) {
//            serializeResult_Int.add(SerializeUtil.bean2Byte(list, ArrayList.class));
//        }
//        long after2 = System.currentTimeMillis();
//        System.out.println("序列化一千次（存了一万个bean的List）：" + (after2 - before2));
    }
}
