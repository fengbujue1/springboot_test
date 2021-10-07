package com.zyj.springboot_test.test.spring.AOP;

import com.zyj.springboot_test.test.spring.AOP.advisor.Advisor;
import com.zyj.springboot_test.test.spring.IOC.BeanFactory;

import java.util.List;

public class DefaultAopProxyFactory implements AopProxyFactory {
    @Override
    public AopProxy createAopProxy(Object bean, String beanName,
                                   List<Advisor> matchedAdvisors, BeanFactory beanFactory) throws Exception {
        if (shouldJdkDynameicProxy(bean, beanName)) {
            return new JdkDynamicAopProxy(beanName, bean, matchedAdvisors, beanFactory);
        } else {
            return new CglibDynamicAopProxy(bean, beanName, matchedAdvisors, beanFactory);
        }
    }

    private boolean shouldJdkDynameicProxy(Object bean, String beanName) {
        return false;
    }
}
