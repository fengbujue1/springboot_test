package com.zyj.springboot_test.test.spring.IOC;

import com.sun.org.apache.bcel.internal.generic.IF_ACMPEQ;
import org.apache.commons.lang.StringUtils;
import org.omg.CORBA.OBJ_ADAPTER;

import java.lang.reflect.Array;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.util.*;

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

    public Object getBean(String name) throws Exception {
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

    private Object doGetBean(BeanDefinition beanDefinition) throws Exception {
        Class<?> type = beanDefinition.getBeanClass();
        Objects.requireNonNull(beanDefinition.getBeanName());
        Object bean = null;
        if(type != null){
            // 通过构造函数
            if(StringUtils.isBlank(beanDefinition.getFactoryMethodName())) {
                bean = createBeanByConstructor(beanDefinition);
            }else{ // 通过静态工厂方式构建对象
                bean = createBeanByStaticFactory(beanDefinition);
            }
        }else{
            // 成员工厂方式构建对象
            bean = createBeanByFactoryBean(beanDefinition);
        }

        // 开始Bean生命周期
        if(StringUtils.isNotBlank(beanDefinition.getInitMethod())) {
            doInitMethod(bean, beanDefinition);
        }

        if (beanDefinition.isSingleton()) {
            beans.put(beanDefinition.getBeanName(), bean);
        }

        return bean;
//        if (beanDefinition.getBeanClass() != null) {
//            //构造器初始化
//            try {
//                return createBeanByConstructor(beanDefinition);
//            } catch (InstantiationException e) {
//                e.printStackTrace();
//            } catch (IllegalAccessException e) {
//                e.printStackTrace();
//            }
//        } else if (StringUtils.isNotBlank(beanDefinition.getFactoryBeanName()) && StringUtils.isNotBlank(beanDefinition.getFactoryMethodName())) {
//            //成员工厂初始化
//            Object factoryBean = getBean(beanDefinition.getFactoryBeanName());
//            String factoryMethodName = beanDefinition.getFactoryMethodName();
//
//            Object[] constructorArgumentValues = getConstructorArgumentValues(beanDefinition);
//
//            Method targetMethod;
//
//            //通过反射对构造方法，参数数量和类型进行精确查找
//            Method[] declaredMethods = factoryBean.getClass().getDeclaredMethods();
//            for (int i = 0; i < declaredMethods.length; i++) {
////                if (classes.length == declaredMethods[i].getParameterCount()) {
////                    Type[] genericParameterTypes = declaredMethods[i].getGenericParameterTypes();
////                    for (int j = 0; j < genericParameterTypes.length; j++) {
////                        if (classes[j].getTypeName().equals(genericParameterTypes[j].getTypeName())) {
////                        }
////                    }
////
////                }
//            }
//
//
//        }
//        return null;
    }

    //调用初始化方法
    private void doInitMethod(Object bean, BeanDefinition bd) throws Exception {
        Method method = bean.getClass().getMethod(bd.getInitMethod(), null);
        method.invoke(bean, null);
    }
    //通过构造函数实例化bean
    private Object createBeanByConstructor(BeanDefinition beanDefinition) throws IllegalAccessException, InstantiationException {
        return beanDefinition.getBeanClass().newInstance();
    }

    //通过成员工厂创建bean
    private Object createBeanByFactoryBean(BeanDefinition bd) throws Exception {
        String factoryBeanName = bd.getFactoryBeanName();
        Object factoryBean = getBean(factoryBeanName);
        Method method = factoryBean.getClass()
                .getMethod(bd.getFactoryMethodName(), null);
        return method.invoke(factoryBean, null);
    }
    //通过静态工厂创建bean
    private Object createBeanByStaticFactory(BeanDefinition bd) throws Exception {
        Class<?> type = bd.getBeanClass();
        Method method = type.getMethod(bd.getFactoryMethodName(), null);
        return method.invoke(type, null);
    }
    /**
     * 参数解析，将引用类型等 到bean容器中找实例依赖
     * @param beanDefinition
     * @return
     */
    private Object[] getConstructorArgumentValues(BeanDefinition beanDefinition) throws Exception {
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
        //注册信息不能为空
        Objects.requireNonNull(beanDefinition);
        Objects.requireNonNull(beanDefinition);
        if (!beanDefinition.validate()) {
            //bean定义信息无效的话，就要抛出异常
            throw new RuntimeException("bean定义信息无效");
        }
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
