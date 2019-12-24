package com.zyj.springboot_test.test.java.serialization.ali_fastjson;

import com.alibaba.fastjson.JSON;
import com.zyj.springboot_test.test.java.serialization.google_protobuff.protobuf.Peson;
import com.zyj.springboot_test.test.java.serialization.model.Numb;
import com.zyj.springboot_test.test.java.serialization.model.Person;
import com.zyj.springboot_test.util.SerializeUtil;
import redis.clients.jedis.Jedis;

import java.util.ArrayList;

public class SimpleTest {
    public static void main(String[] args) {
        class WrapperList {
            public ArrayList<Numb> lists;

            public ArrayList<Numb> getLists() {
                return lists;
            }

            public void setLists(ArrayList<Numb> lists) {

                this.lists = lists;
            }
        }
        Jedis jedis = new Jedis("localhost", 6320);
        Person build = Person.build();


        System.out.println("fastjson:");
        //实验结果：差不多
//        long before = System.currentTimeMillis();
        ArrayList<String> strings = new ArrayList<>();
        long before = System.currentTimeMillis();
        for (int i = 0; i < 10000; i++) {
            strings.add(JSON.toJSONString(Numb.getInstance()));
        }
        long after = System.currentTimeMillis();
        System.out.println("序列化一万次（简单bean）：" +( after - before));

        //int 反序列化
        long before2 = System.currentTimeMillis();
        for (String s:strings) {
            JSON.parseObject(s.getBytes(), Numb.class);
        }
        long after2 = System.currentTimeMillis();
        System.out.println("反序列化一万次（简单bean）：" +( after2 - before2));


        //序列化大对象

        //准备数据
        ArrayList<String> lists = new ArrayList<>();
        ArrayList<WrapperList> temp = new ArrayList<>();
        for (int i = 0; i < 1000; i++) {
            WrapperList wrapperList = new WrapperList();
            wrapperList.setLists(crateList());
            temp.add(wrapperList);
        }

        //开始测试序列化
        long before3 = System.currentTimeMillis();
        for (WrapperList wrapperList : temp) {
            lists.add(JSON.toJSONString(wrapperList));
        }
        long after3 = System.currentTimeMillis();
        System.out.println("序列化一千次（存了一万个bean的List）：" + (after3 - before3));

        ArrayList<WrapperList> serializeResult = new ArrayList<>();


        //反序列化大对象
        long before4 = System.currentTimeMillis();
        for (String s : lists) {
            serializeResult.add(JSON.parseObject(s, WrapperList.class));
        }
        long after4 = System.currentTimeMillis();
        System.out.println("反序列化一千次（存了一万个bean的List）：" + (after4 - before4));

//        long after = System.currentTimeMillis();
//        System.out.println(after - before);
//
//        long before1 = System.currentTimeMillis();
//        Person person = JSON.parseObject(string, Person.class);
//        long after2 = System.currentTimeMillis();
//        System.out.println(after - before);

        //int 序列化也是差不多
//        long before = System.currentTimeMillis();
//        for (int i = 0; i < 1000000; i++) {
//            String string = JSON.toJSONString(Numb.getInstance());
//        }
//        long after = System.currentTimeMillis();
//        System.out.println(after - before);



    }
    private static ArrayList<Numb> crateList() {
        ArrayList<Numb> numbs = new ArrayList<>();
        for (int i = 0; i < 10000; i++) {
            numbs.add(Numb.getInstance());
        }
        return numbs;
    }
}
