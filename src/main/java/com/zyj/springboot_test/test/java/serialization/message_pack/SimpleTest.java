//package com.zyj.springboot_test.test.java.serialization.message_pack;
//
//import com.zyj.springboot_test.test.java.serialization.model.Numb;
//import org.msgpack.MessagePack;
//import org.msgpack.annotation.MessagePackMessage;
//
//import java.io.IOException;
//import java.util.ArrayList;
//import java.util.List;
//
//public class SimpleTest {
//    public static void main(String[] args) throws IOException {
//        @MessagePackMessage
//        class WrapperList {
//            public ArrayList<Numb> lists;
//
//            public ArrayList<Numb> getLists() {
//                return lists;
//            }
//
//            public void setLists(ArrayList<Numb> lists) {
//
//                this.lists = lists;
//            }
//        }
//        System.out.println("messagepack:");
//        MessagePack messagePack = new MessagePack();
//        //一万次序列化
//        ArrayList<byte[]> list = new ArrayList<>();
//        long before = System.currentTimeMillis();
//        for (int i = 0; i < 10000; i++) {
//            list.add(messagePack.write(Numb.getInstance()));
//        }
//        long after = System.currentTimeMillis();
//        System.out.println("序列化一万次（简单bean）："+(after - before));
//
//        //反序列化
//        long before2 = System.currentTimeMillis();
//        for (byte[] bytes : list) {
//            messagePack.read(bytes);
//        }
//        long after2 = System.currentTimeMillis();
//        System.out.println("反序列化一万次（简单bean）：" +( after2 - before2));
//
//        //序列化大对象
//        //准备数据
//        ArrayList<byte[]> lists = new ArrayList<>();
//        ArrayList<WrapperList> temp = new ArrayList<>();
//        for (int i = 0; i < 1000; i++) {
//            WrapperList wrapperList = new WrapperList();
//            wrapperList.setLists(crateList());
//            temp.add(wrapperList);
//        }
//
//        //开始测试
//        long before3 = System.currentTimeMillis();
//        for (WrapperList list1 : temp) {
//            lists.add(messagePack.write(list1));
//        }
//        long after3 = System.currentTimeMillis();
//        System.out.println("序列化一千次（存了一万个bean的List）：" + (after3 - before3));
//
//        //反序列化大对象
//        long before4 = System.currentTimeMillis();
//        for (byte[] bytes : lists) {
//            messagePack.read(bytes);
//        }
//        long after4 = System.currentTimeMillis();
//        System.out.println("反序列化一千次（存了一万个bean的List）：" + (after4 - before4));
//    }
//    private static ArrayList<Numb> crateList() {
//        ArrayList<Numb> numbs = new ArrayList<>();
//        for (int i = 0; i < 10000; i++) {
//            numbs.add(Numb.getInstance());
//        }
//        return numbs;
//    }
//}
