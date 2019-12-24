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
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class SimpleTest {

    public static void main(String[] args) throws UnsupportedEncodingException {
        class WrapperList {
            public ArrayList<Numb> lists;

            public ArrayList<Numb> getLists() {
                return lists;
            }

            public void setLists(ArrayList<Numb> lists) {

                this.lists = lists;
            }
        }

        //protostuff 性能测试


        System.out.println("protostuff:");
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

        //全部从数据库取数据，避免缓存（猜的~没用）
        ArrayList<byte[]> list = new ArrayList<>();
        long before = System.currentTimeMillis();
        for (int i = 0; i < 10000; i++) {
            list.add(SerializeUtil.bean2Byte(Numb.getInstance(), Numb.class));

        }
        long after = System.currentTimeMillis();
        System.out.println("序列化一万次（简单bean）：" + (after - before));

        //反序列化
        long before1 = System.currentTimeMillis();
        for (byte[] bytes : list) {
            SerializeUtil.byte2Bean(bytes, Numb.class);
        }
        long after1 = System.currentTimeMillis();
        System.out.println("反序列化一万次（简单bean）：" + (after1 - before1));

        //序列化大对象
        ArrayList<byte[]> lists = new ArrayList<>();
        ArrayList<WrapperList> temp = new ArrayList<>();
        for (int i = 0; i < 1000; i++) {
            WrapperList wrapperList = new WrapperList();
            wrapperList.setLists(crateList());
            temp.add(wrapperList);
        }
        long before3 = System.currentTimeMillis();
        for (WrapperList wrapperList : temp) {
            lists.add(SerializeUtil.bean2Byte(wrapperList, WrapperList.class));
        }
        long after3 = System.currentTimeMillis();
        System.out.println("序列化一千次（存了一万个bean的List）：" + (after3 - before3));

        //反序列化大对象
        ArrayList<WrapperList>  deserializeResult = new ArrayList<>();
        long before4 = System.currentTimeMillis();
        for (byte[] bytes : lists) {
            deserializeResult.add(SerializeUtil.byte2Bean(bytes, WrapperList.class));
        }
        long after4 = System.currentTimeMillis();
        System.out.println("反序列化一千次（存了一万个bean的List）：" + (after4 - before4));
    }

    private static ArrayList<Numb> crateList() {
        ArrayList<Numb> numbs = new ArrayList<>();
        for (int i = 0; i < 10000; i++) {
            numbs.add(Numb.getInstance());
        }
        return numbs;
    }


    /**
     * 小测一下序列化的性能——只有一次，且数据太小，没有参考价值
     * @param person
     * @param jedis
     * @return
     * @throws UnsupportedEncodingException
     */
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
        jedis.set("person", new String(bytes, "UTF-8"));
        return bytes;
    }

    /**
     * 小测一下反序列化的性能——只有一次，且数据太小，没有参考价值
     * @param clazz
     * @param jedis
     * @param <T>
     */
    public static <T> void test2(Class<T> clazz, Jedis jedis) {

        String person = jedis.get("person");
        byte[] data = person.getBytes();
        //获取当前时间，单位毫秒
        long before = System.currentTimeMillis();
        //获取模板
        Schema<T> schema = RuntimeSchema.getSchema(clazz);

        //获取一个数据
        T message = schema.newMessage();

        //填充数据
        ProtobufIOUtil.mergeFrom(data, message, schema);
        long after = System.currentTimeMillis();
        System.out.println(after - before);
    }
}
