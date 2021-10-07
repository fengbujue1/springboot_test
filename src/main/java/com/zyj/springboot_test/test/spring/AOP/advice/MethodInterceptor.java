package com.zyj.springboot_test.test.spring.AOP.advice;

import java.lang.reflect.Method;

public interface MethodInterceptor extends Advice {
    Object invoke(Method method, Object[] agrs, Object target) throws Throwable;
}
