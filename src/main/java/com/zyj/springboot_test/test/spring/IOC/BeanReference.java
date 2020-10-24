package com.zyj.springboot_test.test.spring.IOC;

/**
 * 用来描述bean依赖关系的数据结构
 */
public class BeanReference {
    public String beanName;

    public String getBeanName() {
        return beanName;
    }

    public void setBeanName(String beanName) {
        this.beanName = beanName;
    }
}
