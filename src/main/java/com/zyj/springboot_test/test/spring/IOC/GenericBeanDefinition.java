package com.zyj.springboot_test.test.spring.IOC;

import java.util.List;

public class GenericBeanDefinition implements BeanDefinition {

    private String beanName;
    private Class<?> beanClass;
    private String factoryMethodName;
    private String factoryBeanName;
    private String staticFactoryMethodName;
    private String scope;
    private List<Object> params;
    private String initMethod;
    private String destroyMethod;


    @Override
    public void setBeanName(String beanName) {
        this.beanName = beanName;
    }

    @Override
    public String getBeanName() {
        return beanName;
    }

    @Override
    public void setBeanClass(Class<?> beanClass) {
        this.beanClass = beanClass;
    }

    @Override
    public Class<?> getBeanClass() {
        return beanClass;
    }

    @Override
    public void setFactoryMethodName(String factoryMethodName) {
        this.factoryMethodName = factoryMethodName;
    }

    @Override
    public String getFactoryMethodName() {
        return factoryMethodName;
    }

    @Override
    public void setFactgoryBeanName(String factgoryBeanName) {
        this.factoryBeanName = factgoryBeanName;
    }

    @Override
    public String getFactoryBeanName() {
        return factoryBeanName;
    }

    @Override
    public void setStaticFactoryMethodName(String staticFactoryMethodName) {
        this.staticFactoryMethodName = staticFactoryMethodName;
    }

    @Override
    public String getStaticFactoryMethodName() {
        return staticFactoryMethodName;
    }

    @Override
    public void setScope(String scope) {
        this.scope = scope;
    }

    @Override
    public String getScope() {
        return scope;
    }

    @Override
    public boolean isSingleton() {
        return SINGLETION.equals(scope);
    }

    @Override
    public boolean isPrototype() {
        return PROTOTYPE.equals(scope);
    }

    @Override
    public void setInitMethod(String initMethod) {
        this.initMethod = initMethod;
    }

    @Override
    public String getInitMethod() {
        return initMethod;
    }

    @Override
    public void setDestroyMethod(String destroyMethod) {
        this.destroyMethod = destroyMethod;
    }

    @Override
    public String getDestroyMethod() {
        return destroyMethod;
    }


    @Override
    public void setParams(List<Object> params) {
        this.params = params;
    }

    @Override
    public List<Object> getParams() {
        return params;
    }
}
