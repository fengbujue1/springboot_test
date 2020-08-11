package com.zyj.springboot_test.test.java.netWork.nio;

import java.nio.Buffer;
import java.nio.ByteBuffer;

public class TestBuffer {
    public static void main(String[] args) {
        testDirectBuffer();
    }
    public static void testDirectBuffer() {
        //使用上和 堆内的内存没啥区别
        ByteBuffer directBuffer = ByteBuffer.allocateDirect(6);//申请一片大小为3的堆外内存

        System.out.println(String.format("初始情况下（申请了6个字节的空间）：capacity:%s,limit:%s,position:%s", directBuffer.capacity(), directBuffer.limit(), directBuffer.position()));
        directBuffer.put((byte) 1);
        directBuffer.put((byte) 2);
        directBuffer.put((byte) 3);
        System.out.println(String.format("插入3个数据后：capacity:%s,limit:%s,position:%s", directBuffer.capacity(), directBuffer.limit(), directBuffer.position()));
        directBuffer.flip();
        System.out.println(String.format("切换为读模式后：capacity:%s,limit:%s,position:%s", directBuffer.capacity(), directBuffer.limit(), directBuffer.position()));
        System.out.println(directBuffer.get());
        System.out.println(directBuffer.get());
        System.out.println(String.format("读取两个数据之后：capacity:%s,limit:%s,position:%s", directBuffer.capacity(), directBuffer.limit(), directBuffer.position()));

        directBuffer.compact();//没有被读取的数据会被推到最左边
        System.out.println(String.format("切换为写模式后：capacity:%s,limit:%s,position:%s", directBuffer.capacity(), directBuffer.limit(), directBuffer.position()));

//        directBuffer.array();//无法将直接内存转化为字节数组，会报错

        directBuffer.clear();//清空数据

        directBuffer.put((byte) 1);
        directBuffer.put((byte) 2);
        directBuffer.put((byte) 3);
        System.out.println(String.format("清空数据，再插入三个数据后：capacity:%s,limit:%s,position:%s", directBuffer.capacity(), directBuffer.limit(), directBuffer.position()));

        System.out.println(directBuffer.get());
        System.out.println(String.format("读取一个数据后：capacity:%s,limit:%s,position:%s", directBuffer.capacity(), directBuffer.limit(), directBuffer.position()));

        directBuffer.flip();
        System.out.println(directBuffer.get());
        directBuffer.mark();//标记一个位置
        System.out.println(String.format("再次读取一个数据后：capacity:%s,limit:%s,position:%s", directBuffer.capacity(), directBuffer.limit(), directBuffer.position()));

        directBuffer.reset();//重置position到上次标记的位置
        System.out.println(directBuffer.get());

        directBuffer.rewind();//重置position到0位置
        System.out.println(directBuffer.get());
        System.out.println(String.format("重置position后再读取一个后：capacity:%s,limit:%s,position:%s", directBuffer.capacity(), directBuffer.limit(), directBuffer.position()));






    }
}
