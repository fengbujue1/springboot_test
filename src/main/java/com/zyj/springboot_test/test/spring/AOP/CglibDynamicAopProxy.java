package com.zyj.springboot_test.test.spring.AOP;

import com.zyj.springboot_test.test.spring.AOP.advisor.Advisor;
import com.zyj.springboot_test.test.spring.IOC.BeanDefinition;
import com.zyj.springboot_test.test.spring.IOC.BeanFactory;
import com.zyj.springboot_test.test.spring.IOC.DefaultBeanFactory;
import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.List;

public class CglibDynamicAopProxy implements AopProxy, MethodInterceptor {
//    private static final Logger logger = LoggerFactory.getLogger(CglibDynamicAopProxy.class);

    static private Enhancer enhancer = new Enhancer();

    private Object target;
    private String beanName;
    private List<Advisor> matchedAdvisors;
    private BeanFactory beanFactory;

    public CglibDynamicAopProxy(Object target, String beanName, List<Advisor> matchedAdvisors, BeanFactory beanFactory) {
        this.target = target;
        this.beanName = beanName;
        this.matchedAdvisors = matchedAdvisors;
        this.beanFactory = beanFactory;
    }

    @Override
    public Object getProxy() {
        return getProxy(target.getClass().getClassLoader());
    }

    @Override
    public Object getProxy(ClassLoader classLoader) {

//        if (logger.isDebugEnabled()) {
        System.out.println("为" + target + "创建cglib代理。");
//        }
        Class<?> superClass = this.target.getClass();
        enhancer.setSuperclass(superClass);
        enhancer.setInterfaces(this.getClass().getInterfaces());
        enhancer.setCallback(this);
        Constructor<?> constructor = null;
        try {
            constructor = superClass.getConstructor(new Class<?>[] {});
        } catch (NoSuchMethodException | SecurityException e) {

        }
        if (constructor != null) {
            return enhancer.create();
        } else {
            BeanDefinition bd = ((DefaultBeanFactory) beanFactory).getBeanDefition(beanName);
            return enhancer.create(bd.getConstructor().getParameterTypes(), bd.getParams().toArray());
        }
    }

    @Override
    public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
        return AopProxyUtils.applyAdvices(target, method, objects, matchedAdvisors, o, beanFactory);

    }
}
