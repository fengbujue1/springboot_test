package com.zyj.springboot_test.util;

import com.dyuproject.protostuff.LinkedBuffer;
import com.dyuproject.protostuff.ProtobufIOUtil;
import com.dyuproject.protostuff.Schema;
import com.dyuproject.protostuff.runtime.RuntimeSchema;

public class SerializeUtil {

    private static int BUFFER_SIZE = 1024;
    public static ThreadLocal<LinkedBuffer> localBuffer = new ThreadLocal<LinkedBuffer>() {
        @Override
        protected LinkedBuffer initialValue() {
            return LinkedBuffer.allocate(BUFFER_SIZE);
        }
    };
    /**
     * 反序列化
     * @param data
     * @param clazz
     * @param <T>
     * @return
     */
    public static <T> T byte2Bean(byte[] data, Class<T> clazz) {
        //获取模板
        Schema<T> schema = RuntimeSchema.getSchema(clazz);

        //获取一个数据
        T message = schema.newMessage();

        //填充数据
        ProtobufIOUtil.mergeFrom(data,message,schema);
        return message;
    }

    /**
     * 序列化
     *
     * @param obj
     * @param <T>
     * @return
     */
    public static <T> byte[] bean2Byte(T obj, Class<?> clazz) {
        //此处用了强转，不知道会不会出问题，项目里面也是这样用的
        Schema<T> schema =( Schema<T>) RuntimeSchema.getSchema(clazz);
        byte[] bytes = ProtobufIOUtil.toByteArray(obj, schema, getBuffer());
        return bytes;
    }

    //获取一个线程安全的 LinkedBuffer,可是为什么要clear()??
    private static LinkedBuffer getBuffer() {
        return localBuffer.get().clear();
    }

}
