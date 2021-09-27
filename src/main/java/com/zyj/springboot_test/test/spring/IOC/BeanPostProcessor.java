package com.zyj.springboot_test.test.spring.IOC;

import com.zyj.springboot_test.test.spring.AOP.advisor.Advisor;

/**
 * bean 初始化过程中的处理器
 */
public interface BeanPostProcessor {
    void postProcessBeforeInitialization(Object bean, String beanName);
    void postProcessAfterInitialization(Object bean, String beanName);

}
