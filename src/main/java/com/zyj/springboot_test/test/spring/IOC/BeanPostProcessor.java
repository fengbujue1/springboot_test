package com.zyj.springboot_test.test.spring.IOC;

import com.zyj.springboot_test.test.spring.AOP.advisor.Advisor;

/**
 * bean 初始化过程中的处理器
 */
public interface BeanPostProcessor {
   default Object postProcessBeforeInitialization(Object bean, String beanName)throws Exception {
       return bean;
   }
    default Object postProcessAfterInitialization(Object bean, String beanName) throws Exception {
        return bean;
    };

}
