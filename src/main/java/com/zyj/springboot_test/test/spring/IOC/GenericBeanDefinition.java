package com.zyj.springboot_test.test.spring.IOC;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.List;

public class GenericBeanDefinition implements BeanDefinition {

    private String beanName;
    private Class<?> beanClass;
    private String factoryMethodName;
    private String factoryBeanName;
    private String staticFactoryMethodName;
    private String scope = BeanDefinition.SINGLETION;
    private List<Object> params;//构造参数
    private List<Object> paramsRealValues;//构造参数的真实值
    private List<PropertyValue> propertiesValues;//依赖
    private Constructor<?> constructor;//构造器，用于原型模式下，多次创建实例的时候，不用再去查找具体的构造器
    private Method staticFactoryMethod;//静态工厂方法，用于原型模式下，多次创建实例的时候，不用再去查找具体的静态工厂方法
    private Method factoryBeanMethod;//成员工厂方法，用于原型模式下，多次创建实例的时候，不用再去查找具体的静态工厂方法
    private String initMethod;
    private String destroyMethod;

    @Override
    public void setParamsRealValues(List<Object> params) {
        paramsRealValues = params;
    }

    @Override
    public List<Object> getParamsRealValues() {
        return paramsRealValues;
    }

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

    @Override
    public Constructor<?> getConstructor() {
        return constructor;
    }

    @Override
    public void setConstructor(Constructor<?> constructor) {
        this.constructor = constructor;
    }

    @Override
    public Method getStaticFactoryMethod() {
        return staticFactoryMethod;
    }

    @Override
    public void setStaticFactoryMethod(Method staticFactoryMethod) {
        this.staticFactoryMethod = staticFactoryMethod;
    }

    @Override
    public Method getFactoryBeanMethod() {
        return factoryBeanMethod;
    }

    @Override
    public void setFactoryBeanMethod(Method factoryBeanMethod) {
        this.factoryBeanMethod = factoryBeanMethod;
    }

    @Override
    public void setPropertiesValues(List<PropertyValue> propertiesValues) {
        this.propertiesValues = propertiesValues;
    }

    @Override
    public List<PropertyValue> getPropertiesValues() {
        return propertiesValues;
    }
}
