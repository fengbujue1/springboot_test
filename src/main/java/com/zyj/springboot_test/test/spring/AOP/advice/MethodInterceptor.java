package com.zyj.springboot_test.test.spring.AOP.advice;

import java.lang.reflect.Method;

public interface MethodInterceptor extends Advice {
    Object intercept(Method method, Object[] agrs, Object target);
}
