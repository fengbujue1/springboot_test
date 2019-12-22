package com.zyj.springboot_test.test.java.genericity;

public class ReturnType {
    public static void main(String[] args) {
        Model_1 bean = getBean(Model_1.class);
        System.out.println(bean);
    }

    public static  <T extends Object> T getBean(Class<T> clazz) {
        T t = null;
        try {
            t = clazz.newInstance();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return t;
    }
}
