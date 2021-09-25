package com.zyj.springboot_test.test.spring.AOP.advice;

import java.lang.reflect.Method;

public interface AfterReturningAdvice extends Advice  {
    void afterReturning(Method method,Object[] agrs,Object target);
}
