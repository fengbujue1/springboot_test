package com.zyj.springboot_test.test.java.serialization.google_protobuff.protostuff;

import com.dyuproject.protostuff.LinkedBuffer;
import com.dyuproject.protostuff.ProtobufIOUtil;
import com.dyuproject.protostuff.Schema;
import com.dyuproject.protostuff.runtime.RuntimeSchema;
import com.zyj.springboot_test.test.java.serialization.model.Person;
import com.zyj.springboot_test.util.SerializeUtil;

public class SimpleTest {
    public static void main(String[] args) {
        //准备数据
        Person person = Person.build();
        long before = System.currentTimeMillis();
        byte[] bytes = SerializeUtil.bean2Byte(person, Person.class);
        long after = System.currentTimeMillis();
        System.out.println(after - before);


//        test1(person);
//        byte[] bytes = test1(person);

//        long before1 = System.currentTimeMillis();
//        Person person1 = SerializeUtil.byte2Bean(bytes, Person.class);
//        long after1 = System.currentTimeMillis();
//        System.out.println(after1 - before1);



        test2(bytes,Person.class);

    }

    public static byte[] test1(Person person) {
        //获取当前时间，单位毫秒
        long before = System.currentTimeMillis();
        //获取schema 模板
        Schema<Person> schema = RuntimeSchema.getSchema(Person.class);

        //申请一段空间
        LinkedBuffer buffer = LinkedBuffer.allocate(1024);
        byte[] bytes = ProtobufIOUtil.toByteArray(person, schema, buffer);
        long after = System.currentTimeMillis();
        System.out.println(after - before);
        return bytes;
    }
    public static<T> void test2(byte[] data,Class<T> clazz) {
        //获取当前时间，单位毫秒
        long before = System.currentTimeMillis();
        //获取模板
        Schema<T> schema = RuntimeSchema.getSchema(clazz);

        //获取一个数据
        T message = schema.newMessage();

        //填充数据
        ProtobufIOUtil.mergeFrom(data,message,schema);
        long after = System.currentTimeMillis();
        System.out.println(after - before);
    }
}
