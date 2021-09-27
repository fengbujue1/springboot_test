package com.zyj.springboot_test.test.spring.IOC;

public interface BeanFactory {
    Object getBean(String name) throws Exception;

    /**
     * 注册 bean 初始化过程中的处理器
     */
    void registerBeanPostProcessor(BeanPostProcessor beanPostProcessor);
}
