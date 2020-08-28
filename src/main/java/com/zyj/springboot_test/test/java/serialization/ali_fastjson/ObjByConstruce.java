package com.zyj.springboot_test.test.java.serialization.ali_fastjson;

import com.alibaba.fastjson.JSON;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

public class ObjByConstruce {
    public static void main(String[] args) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
        Constructor<Person2> constructor = Person2.class.getConstructor(String.class, int.class, int.class);
        constructor.setAccessible(true);
        Persopn zhansgan = constructor.newInstance("zhansgan", 18, 180);

        String s = JSON.toJSONString(zhansgan);
        System.out.println(s);
    }

}
