package com.zyj.springboot_test.test.java.serialization.google_protobuff.protobuf;

import com.google.protobuf.InvalidProtocolBufferException;

public class SimpleTest {
    public static void main(String[] args) {
        long before = System.currentTimeMillis();
        for (int i = 0; i < 10000; i++) {
            Peson.Person.Builder builder = Peson.Person.newBuilder();
            builder.setAge(20).setId(1).setName("zhou");

            //序列化为字符数组
            byte[] bytes = builder.build().toByteArray();

            try {
                Peson.Person person = Peson.Person.parseFrom(bytes);
                System.out.println(person);
            } catch (InvalidProtocolBufferException e) {
                e.printStackTrace();
            }
        }
        long after = System.currentTimeMillis();
        System.out.println("序列化一万次（简单bean）：" + (after - before));

    }
}
