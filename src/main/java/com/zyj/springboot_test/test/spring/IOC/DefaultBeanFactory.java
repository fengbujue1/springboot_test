package com.zyj.springboot_test.test.spring.IOC;

import org.apache.commons.lang.StringUtils;
import org.omg.CORBA.OBJ_ADAPTER;

import java.lang.reflect.Array;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

/**
 * 懒加载的bean工厂，第一次获取Bean的时候完成初始化
 */
public class DefaultBeanFactory implements BeanDefinitionRegistry,BeanFactory{

    Map<String,Object> beans;
    Map<String,BeanDefinition> definitionMap;

    public DefaultBeanFactory() {
        this.beans = new HashMap<>();
        this.definitionMap = new HashMap<>();
    }

    public Object getBean(String name) {
        Object targetBean = this.beans.get(name);

        if (targetBean != null) {
            return targetBean;
        }

        BeanDefinition beanDefinition = definitionMap.get(name);
        if (beanDefinition != null) {
            return doGetBean(beanDefinition);
        } else {
            throw new RuntimeException("bean is not define");
        }

    }

    private Object doGetBean(BeanDefinition beanDefinition) {
        if (beanDefinition.getBeanClass() != null) {
            //构造器初始化
            try {
                return beanDefinition.getBeanClass().newInstance();

            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        } else if (StringUtils.isNotBlank(beanDefinition.getFactoryBeanName()) && StringUtils.isNotBlank(beanDefinition.getFactoryMethodName())) {
            //成员工厂初始化
            Object factoryBean = getBean(beanDefinition.getFactoryBeanName());
            String factoryMethodName = beanDefinition.getFactoryMethodName();

            Object[] constructorArgumentValues = getConstructorArgumentValues(beanDefinition);

            Method targetMethod;

            //通过反射对构造方法，参数数量和类型进行精确查找
            Method[] declaredMethods = factoryBean.getClass().getDeclaredMethods();
            for (int i = 0; i < declaredMethods.length; i++) {
//                if (classes.length == declaredMethods[i].getParameterCount()) {
//                    Type[] genericParameterTypes = declaredMethods[i].getGenericParameterTypes();
//                    for (int j = 0; j < genericParameterTypes.length; j++) {
//                        if (classes[j].getTypeName().equals(genericParameterTypes[j].getTypeName())) {
//                        }
//                    }
//
//                }
            }


        }
        return null;
    }

    /**
     * 参数解析，将引用类型等 到bean容器中找实例依赖
     * @param beanDefinition
     * @return
     */
    private Object[] getConstructorArgumentValues(BeanDefinition beanDefinition) {
        List<Object> params = beanDefinition.getParams();

        Object[] constructorArgumentValues = new Class[params.size()];
        int indx = 0;

        for (Object param : params) {
            if (param instanceof BeanReference) {
                //如果是引用,则去容器中尝试获取bean
                constructorArgumentValues[++indx] = getBean(((BeanReference) param).getBeanName());
            } else if (param instanceof Object[]) {
                //TODO 处理数组
            } else if (param instanceof Map) {
                //TODO 处理map
            } else {
                constructorArgumentValues[indx++] = param;
            }

        }

        return constructorArgumentValues;
    }

    @Override
    public void register(String beanName, BeanDefinition beanDefinition) {
        definitionMap.put(beanName, beanDefinition);//对于重名的bean,后定义的替换掉先定义的
    }

    @Override
    public BeanDefinition getBeanDefition(String beanName) {
        return definitionMap.get(beanName);
    }

    @Override
    public boolean contains(String beanName) {
        return beans.containsKey(beanName);
    }
}
