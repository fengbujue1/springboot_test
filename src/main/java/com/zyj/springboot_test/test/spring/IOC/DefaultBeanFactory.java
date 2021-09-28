package com.zyj.springboot_test.test.spring.IOC;

import org.apache.commons.lang.StringUtils;

import java.io.Closeable;
import java.lang.reflect.*;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 懒加载的bean工厂，第一次获取Bean的时候完成初始化
 */
public class DefaultBeanFactory implements BeanDefinitionRegistry,BeanFactory, Closeable {

    private Map<String,Object> beans;
    private Map<String,BeanDefinition> definitionMap;
    private Set<String> ingBeans;
    private List<BeanPostProcessor> beanPostProcessors;

    public DefaultBeanFactory() {
        this.beans = new ConcurrentHashMap<>();
        this.definitionMap = new ConcurrentHashMap<>();
        this.ingBeans = Collections.newSetFromMap(new ConcurrentHashMap<>());
    }

    public Object getBean(String name) throws Exception {

        Object targetBean = doGetBean(name);
        //如果缓存中没有则初始化
        BeanDefinition beanDefinition = definitionMap.get(name);

        setPropertiesValues(beanDefinition, targetBean);

        beanPostProcessorBeforInit(targetBean,name);
        // 开始Bean生命周期
        if(StringUtils.isNotBlank(beanDefinition.getInitMethod())) {
            doInitMethod(targetBean, beanDefinition);
        }
        return targetBean;

    }

    @Override
    public void registerBeanPostProcessor(BeanPostProcessor beanPostProcessor) {
        beanPostProcessors.add(beanPostProcessor);
    }

    protected Object doGetBean(String beanName) throws Exception {
        Object targetBean = this.beans.get(beanName);
        if (targetBean != null) {
            return targetBean;
        }

        BeanDefinition beanDefinition = definitionMap.get(beanName);
        Class<?> type = beanDefinition.getBeanClass();
        Objects.requireNonNull(beanDefinition.getBeanName());
        Object bean = null;
        //解决循环依赖问题
        if (ingBeans.contains(beanDefinition.getBeanName())) {
            throw new RuntimeException("出现了循环依赖" + ingBeans);
        }else {
            ingBeans.add(beanDefinition.getBeanName());
        }


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

        ingBeans.remove(beanDefinition.getBeanName());



        if (beanDefinition.isSingleton()) {
            beans.put(beanDefinition.getBeanName(), bean);
        }



        return bean;
    }
    private void beanPostProcessorBeforInit(Object bean, String beanName) {
        for (BeanPostProcessor beanPostProcessor : beanPostProcessors) {
            beanPostProcessor.postProcessBeforeInitialization(bean, beanName);
        }
    }
    private void beanPostProcessorAfterInit() {

    }

    //设置属性依赖
    private void setPropertiesValues(BeanDefinition beanDefinition, Object instance) throws Exception{
        List<PropertyValue> propertiesValues = beanDefinition.getPropertiesValues();

        if (propertiesValues == null || propertiesValues.size() == 0) {
            return;
        }
        Class<?> aClass = instance.getClass();
        for (PropertyValue propertyValue : propertiesValues) {
            String name = propertyValue.getName();
            Object value = propertyValue.getValue();
            if (name == null || value == null) {
                continue;
            }
            Object realVale = null;
            if (value instanceof BeanReference) {
                //引用类型
                BeanReference beanReference = (BeanReference) value;
                Object bean = doGetBean(beanReference.getBeanName());
                realVale = bean;
            } else if (value instanceof Map) {
                //TODO
            } else if (value instanceof List) {
                //TODO
            } else {
                //基本数据类型
                realVale = value;
            }
            Field declaredField = aClass.getDeclaredField(propertyValue.getName());
            declaredField.setAccessible(true);
            declaredField.set(instance, realVale);
        }

    }

    //调用初始化方法
    private void doInitMethod(Object bean, BeanDefinition bd) throws Exception {
        Method method = bean.getClass().getMethod(bd.getInitMethod(), null);
        method.invoke(bean, null);
    }
    //通过构造函数实例化bean
    private Object createBeanByConstructor(BeanDefinition beanDefinition) throws Exception {
        List<Object> params = beanDefinition.getParams();
        if (params == null || params.size() == 0) {
            return beanDefinition.getBeanClass().newInstance();
        }
        Constructor<?> constructor = getConstructor(beanDefinition);
        return constructor.newInstance(getParamsRealValues(beanDefinition));
    }

    //获取参数类型
    private Class[] getParamsTypes(BeanDefinition beanDefinition) throws Exception {
        List<Object> params = beanDefinition.getParams();
        Object[] paramsArr = params.toArray();
        Class[] paramsTypes = new Class[paramsArr.length];
        for (int i = 0; i < paramsArr.length; i++) {
            if (paramsArr[i] instanceof BeanReference) {
                //引用类型
                BeanReference beanReference = (BeanReference) paramsArr[i];
                Object bean = doGetBean(beanReference.getBeanName());
                paramsTypes[i] = bean.getClass();
            } else if (paramsArr[i] instanceof Map) {
                //TODO
                paramsTypes[i] = Map.class;
            } else if (paramsArr[i] instanceof List) {
                //TODO
                paramsTypes[i] = List.class;
            } else {
                //基本数据类型
                paramsTypes[i] = paramsArr[i].getClass();
            }
        }

        return paramsTypes;
    }

    //获取参数的实际值
    private Object[] getParamsRealValues(BeanDefinition beanDefinition) throws Exception {
        List<Object> params = beanDefinition.getParams();
        if (params == null || params.size() == 0) {
            return null;
        }
        Object[] paramsArr = params.toArray();
        Object[] paramsRealValues = new Object[paramsArr.length];
        for (int i = 0; i < paramsArr.length; i++) {
            if (paramsArr[i] instanceof BeanReference) {
                //引用类型
                BeanReference beanReference = (BeanReference) paramsArr[i];
                Object bean = doGetBean(beanReference.getBeanName());
                if (bean != null) {
                    paramsRealValues[i] = bean;
                } else {
                    throw new Exception("无效的依赖");
                }
            } else {
                //基本数据类型
                paramsRealValues[i] = paramsArr[i];
            }
        }
        return paramsRealValues;
    }

    //根据bean定义信息获取构造器
    private Constructor<?> getConstructor(BeanDefinition beanDefinition) throws Exception{
        Class[] paramsTypes = getParamsTypes(beanDefinition);
        Constructor<?> constructor = null;
        constructor = beanDefinition.getConstructor();
        if (constructor != null) {
            return constructor;
        }
        try {
            constructor = beanDefinition.getBeanClass().getConstructor(paramsTypes);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (constructor != null) {
            return constructor;
        }
        Constructor<?>[] constructors = beanDefinition.getBeanClass().getConstructors();
        outer:for (int i = 0; i < constructors.length; i++) {
            Class<?>[] parameterTypes = constructors[i].getParameterTypes();
            if (parameterTypes.length != paramsTypes.length) {
                continue;
            }
            for (int j = 0; j < parameterTypes.length; j++) {
                if (!parameterTypes[j].isAssignableFrom(paramsTypes[j])) {
                    continue outer;
                }
            }
            constructor = constructors[i];
            break;
        }
        if (constructor != null) {
            if (beanDefinition.isSingleton()) {
                beanDefinition.setConstructor(constructor);
            }
            return constructor;
        } else {
            throw new Exception("构造参数传入不对！！");
        }

    }

    //通过成员工厂创建bean
    private Object createBeanByFactoryBean(BeanDefinition bd) throws Exception {
        String factoryBeanName = bd.getFactoryBeanName();
        Object factoryBean = getBean(factoryBeanName);
        Object[] paramsRealValues = getParamsRealValues(bd);
        Method factoryBeanMethod = bd.getFactoryBeanMethod();
        if (factoryBeanMethod != null) {
            return factoryBeanMethod.invoke(factoryBean, paramsRealValues);
        } else {
            factoryBeanMethod = getFactoryMethod(bd, factoryBean.getClass());
        }
        return factoryBeanMethod.invoke(factoryBean, paramsRealValues);
    }
    //通过静态工厂创建bean
    private Object createBeanByStaticFactory(BeanDefinition bd) throws Exception {
        Method factoryMethod = null;
        Class<?> type = bd.getBeanClass();
        Object[] paramsRealValues = getParamsRealValues(bd);
        factoryMethod = bd.getStaticFactoryMethod();
        if (factoryMethod != null) {
            return factoryMethod.invoke(type, paramsRealValues);
        } else {
            factoryMethod = getFactoryMethod(bd,type);
        }
        return factoryMethod.invoke(type, paramsRealValues);
    }

    private Method getFactoryMethod(BeanDefinition beanDefinition,Class<?> clazz) throws Exception{
        Class[] paramsTypes = getParamsTypes(beanDefinition);
        Method factoryMethod = null;
        try {
            factoryMethod = clazz.getMethod(beanDefinition.getFactoryMethodName(),paramsTypes);
        } catch (Exception e) {
//            e.printStackTrace();
        }
        if (factoryMethod != null) {
            return factoryMethod;
        }
        Method[] factoryMethods = clazz.getMethods();
        outer:for (int i = 0; i < factoryMethods.length; i++) {
            Class<?>[] parameterTypes = factoryMethods[i].getParameterTypes();
            if (parameterTypes.length != paramsTypes.length) {
                continue;
            }
            for (int j = 0; j < parameterTypes.length; j++) {
                if (!parameterTypes[j].isAssignableFrom(paramsTypes[j])) {
                    continue outer;
                }
            }
            factoryMethod = factoryMethods[i];
            break;
        }
        if (factoryMethod != null) {
            if (!beanDefinition.isSingleton()) {
                beanDefinition.setStaticFactoryMethod(factoryMethod);
            }
            return factoryMethod;
        } else {
            throw new Exception("构造参数传入不对！！");
        }

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
        if (!beanDefinition.validate()) {
            //bean定义信息无效的话，就要抛出异常
            throw new RuntimeException("bean定义信息无效");
        }
        if (StringUtils.isBlank(beanDefinition.getBeanName())) {
            beanDefinition.setBeanName(beanName);
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

    @Override
    public void close() {
        try {
            //只针对单例bean进行销毁
            Set<Map.Entry<String, BeanDefinition>> entries = definitionMap.entrySet();
            for (Map.Entry<String, BeanDefinition> entry : entries) {
                BeanDefinition beanDefinition = entry.getValue();
                if (beanDefinition.isSingleton() && StringUtils.isNotBlank(beanDefinition.getDestroyMethod())) {
                    Object bean = beans.get(beanDefinition.getBeanName());
                    if (bean != null) {
                        Method destrouMethod = bean.getClass().getDeclaredMethod(beanDefinition.getDestroyMethod());
//                        Method destrouMethod = bean.getClass().getMethod(beanDefinition.getDestroyMethod());
                        destrouMethod.invoke(bean, null);
                    }
                }
            }
        } catch (Exception e) {
            System.out.println("容器关闭异常：");
            e.printStackTrace();
        }
    }
}
