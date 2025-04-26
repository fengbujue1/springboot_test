package com.zyj.springboot_test.test.java.classload;

import java.io.IOException;
import java.util.jar.JarFile;

public class MyClassLoader extends ClassLoader {
    @Override
    protected Class<?> findClass(String name) throws ClassNotFoundException {
        // 自定义类加载逻辑
        try {
            JarFile jarFile = new JarFile("");
//            jarFile.ll
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
//        byte[] data = loadClassData(name); // 加载类字节码
        return null;
    }

    @Override
    public Class<?> loadClass(String name) throws ClassNotFoundException {
        // 在loadClass中先委派父类加载器
        Class<?> clazz = findLoadedClass(name); // 先检查类是否已经加载
        if (clazz == null) {
            try {
                clazz = getParent().loadClass(name); // 委派给父类加载器
            } catch (ClassNotFoundException e) {
                clazz = findClass(name); // 如果父类加载器不能加载，自己加载
            }
        }
        return clazz;
    }
}
