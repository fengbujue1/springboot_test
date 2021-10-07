package com.zyj.springboot_test.test.spring.AOP.pointcut;

import java.lang.reflect.Method;

public interface Pointcut {
    //是否可以匹配类
    boolean matchClass(Class<?> cls);

    //是否可以匹配指定方法
    boolean matchMethod(Method method, Class<?> targetClass);
}
