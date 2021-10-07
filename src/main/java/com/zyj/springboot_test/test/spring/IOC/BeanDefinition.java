package com.zyj.springboot_test.test.spring.IOC;

import org.apache.commons.lang.StringUtils;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.List;

public interface BeanDefinition {
    public static final String SINGLETION = "singletion";
    public static final String PROTOTYPE = "prototype";

    //构造器参数（可能存在引用类型的参数），构造器
    void setParams(List<Object> params);
    List<Object> getParams();

    //构造参数的真实值，用于在aop中实现代理的时候使用
    void setParamsRealValues(List<Object> params);
    List<Object> getParamsRealValues();

    Constructor<?> getConstructor();
    void setConstructor(Constructor<?> constructor);

    Method getStaticFactoryMethod();
    void setStaticFactoryMethod(Method staticFactoryMethod);
    Method getFactoryBeanMethod();
    void setFactoryBeanMethod(Method factoryBeanMethod);

    void setPropertiesValues(List<PropertyValue> propertiesValues);
    List<PropertyValue> getPropertiesValues();



    void setBeanName(String beanName);
    String getBeanName();

    void setBeanClass(Class<?> beanClass);
    Class<?> getBeanClass();

    void setFactoryMethodName(String factoryMethodName);
    String getFactoryMethodName();
    void setFactgoryBeanName(String factgoryBeanName);
    String getFactoryBeanName();

    void setStaticFactoryMethodName(String staticFactoryName);
    String getStaticFactoryMethodName();

    //单例相关
    void setScope(String scope);
    String getScope();
    boolean isSingleton();
    boolean isPrototype();

    //初始化和销毁方法
    void setInitMethod(String initMethod);
    String getInitMethod();
    void setDestroyMethod(String destroyMethod);
    String getDestroyMethod();




    default boolean validate() {

        if (this.getBeanClass() == null) {
            if (StringUtils.isBlank(this.getFactoryBeanName()) || StringUtils.isBlank(this.getFactoryMethodName())) {
                return false;
            }
        } else if (!StringUtils.isBlank(this.getFactoryBeanName()) && this.getBeanClass() != null) {
            //既传入了class又传入了成员工厂，不知道用哪种方式实例化bean
            return false;
        }

        return true;

    }

}
