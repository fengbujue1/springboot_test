package com.zyj.springboot_test.test.spring.IOC;

public interface BeanDefinitionRegistry {
    void register(String beanName,BeanDefinition beanDefinition);

    BeanDefinition getBeanDefition(String beanName);

    boolean contains(String beanName);
}
