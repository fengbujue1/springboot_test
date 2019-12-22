package com.zyj.springboot_test.test.java.serialization.google_protobuff.protobuf;

import com.google.protobuf.InvalidProtocolBufferException;

public class SimpleTest {
    public static void main(String[] args) {
        Peson.Person.Builder builder = Peson.Person.newBuilder();
        builder.setAge(20).setId(1).setName("zhou");

        //序列化为字符数组
        byte[] bytes = builder.build().toByteArray();
        System.out.println(bytes);

        try {
            Peson.Person person = Peson.Person.parseFrom(bytes);
            System.out.println(person);
        } catch (InvalidProtocolBufferException e) {
            e.printStackTrace();
        }

    }
}
