package com.zyj.springboot_test.test.java.serialization.google_protobuff.protostuff;

import com.alibaba.fastjson.JSON;
import com.dyuproject.protostuff.LinkedBuffer;
import com.dyuproject.protostuff.ProtobufIOUtil;
import com.dyuproject.protostuff.Schema;
import com.dyuproject.protostuff.runtime.RuntimeSchema;
import com.zyj.springboot_test.test.java.serialization.model.Numb;
import com.zyj.springboot_test.test.java.serialization.model.Person;
import com.zyj.springboot_test.util.SerializeUtil;
import redis.clients.jedis.Jedis;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

public class SimpleTest {
    public static void main(String[] args) throws UnsupportedEncodingException {
        //protostuff 性能测试

        Jedis jedis = new Jedis("localhost", 6320);

        //准备数据
        Person person = Person.build();
        Numb numb = Numb.getInstance();

        //protostuff
//        test1(person,jedis);
//        test2(Person.class,jedis);

        //序列化
//        long before = System.currentTimeMillis();
//        for (int i = 0; i < 1000000; i++) {
//            SerializeUtil.bean2Byte(Numb.getInstance(), Numb.class);
//        }
//        long after = System.currentTimeMillis();
//        System.out.println(after - before);

        //反序列化
        //全部从数据库取数据，避免缓存（猜的~没用）
        ArrayList<byte[]> list = new ArrayList<>();
        for (int i = 0; i < 10000; i++) {
            list.add(SerializeUtil.bean2Byte(Numb.getInstance(), Numb.class));

        }

        long before = System.currentTimeMillis();
        for (byte[] bytes : list) {
            SerializeUtil.byte2Bean(bytes, Numb.class);
        }
        long after = System.currentTimeMillis();
        System.out.println(after - before);

    }

    public static byte[] test1(Person person, Jedis jedis) throws UnsupportedEncodingException {
        //获取当前时间，单位毫秒
        long before = System.currentTimeMillis();
        //获取schema 模板
        Schema<Person> schema = RuntimeSchema.getSchema(Person.class);

        //申请一段空间
        LinkedBuffer buffer = LinkedBuffer.allocate(1024);
        byte[] bytes = ProtobufIOUtil.toByteArray(person, schema, buffer);
        long after = System.currentTimeMillis();
        System.out.println(after - before);
        jedis.set("person", new String(bytes,"UTF-8"));
        return bytes;
    }
    public static<T> void test2(Class<T> clazz,Jedis jedis) {

        String person = jedis.get("person");
        byte[] data = person.getBytes();
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
