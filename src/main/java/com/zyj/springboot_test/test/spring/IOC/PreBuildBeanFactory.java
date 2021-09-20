package com.zyj.springboot_test.test.spring.IOC;

import org.apache.commons.lang.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * 对于单例Bean，提前实例化
 */
public class PreBuildBeanFactory extends DefaultBeanFactory {
    List<String> beanNames = new ArrayList<>();
    @Override
    public void register(String beanName, BeanDefinition beanDefinition) {
        super.register(beanName, beanDefinition);
        synchronized (beanNames) {
            beanNames.add(beanName);
        }
    }

    public void preInstantiateSingletons() throws Exception {
        synchronized (beanNames) {
            for (String beanName : beanNames) {
                if (this.getBeanDefition(beanName).isSingleton()) {
                    getBean(beanName);
                }
            }
        }
    }
}
