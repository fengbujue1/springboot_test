package com.zyj.springboot_test.test.spring.AOP;

import com.zyj.springboot_test.test.spring.AOP.advisor.Advisor;
import com.zyj.springboot_test.test.spring.IOC.BeanFactory;

import java.util.List;

/**
 * AOP代理借口的工厂模式接口
 */
public interface AopProxyFactory {
    AopProxy createAopProxy(Object bean, String beanName, List<Advisor> matchedAdvisors, BeanFactory beanFactory) throws Exception;

    static AopProxyFactory getAopProxyFactory() {
        return new DefaultAopProxyFactory();
    }
}
