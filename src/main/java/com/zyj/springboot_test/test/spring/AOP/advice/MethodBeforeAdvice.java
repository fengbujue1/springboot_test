package com.zyj.springboot_test.test.spring.AOP.advice;

import java.lang.reflect.Method;

public interface MethodBeforeAdvice extends Advice {
    void before(Method method, Object[] agrs, Object target);
}
