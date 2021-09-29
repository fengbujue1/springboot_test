package com.zyj.springboot_test.test.spring.AOP;

/**
 * AOP代理接口，用来创建代理对象
 */
public interface AopProxy {
    Object getProxy();

    Object getProxy(ClassLoader classLoader);

}
