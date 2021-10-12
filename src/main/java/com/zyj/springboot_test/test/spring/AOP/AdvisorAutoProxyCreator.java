package com.zyj.springboot_test.test.spring.AOP;

import com.zyj.springboot_test.test.spring.AOP.advisor.Advisor;
import com.zyj.springboot_test.test.spring.AOP.advisor.AdvisorRegister;
import com.zyj.springboot_test.test.spring.AOP.advisor.PointcutAdvisor;
import com.zyj.springboot_test.test.spring.AOP.pointcut.Pointcut;
import com.zyj.springboot_test.test.spring.IOC.BeanFactory;
import com.zyj.springboot_test.test.spring.IOC.BeanFactoryAware;
import com.zyj.springboot_test.test.spring.IOC.BeanPostProcessor;
import javafx.beans.binding.ObjectExpression;
import jodd.util.CollectionUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.util.ClassUtils;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Method;
import java.util.*;

public class AdvisorAutoProxyCreator implements BeanPostProcessor, AdvisorRegister, BeanFactoryAware {

    private List<Advisor> advisors;
    // 当前的bean工厂
    private BeanFactory beanFactory;

    public AdvisorAutoProxyCreator() {
        this.advisors = new ArrayList<>();
    }

    @Override
    public void registorAdvistor(Advisor advisor) {
        advisors.add(advisor);
    }

    @Override
    public List<Advisor> getAdvistors() {
        return advisors; 
    }

    public void setBeanFactory(BeanFactory beanFactory) {
        this.beanFactory = beanFactory;
    }

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws Exception {
        List<Advisor> matchedAdvisor =
                getMatchedAdvisor(beanName, bean);
        if (matchedAdvisor == null || matchedAdvisor.isEmpty()) {
            return bean;
        }
        //TODO 实际的代理增强
        return this.getProxy(bean, beanName, matchedAdvisor);

    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws Exception{
        List<Advisor> matchedAdvisor = getMatchedAdvisor(beanName, bean);
        if (matchedAdvisor == null || matchedAdvisor.isEmpty()) {
            return bean;
        }
        //TODO 实际的代理增强
        return this.getProxy(bean, beanName, matchedAdvisor);
    }

    /**
     * 获取匹配的通知
     *
     * 针对的是bean的所有方法，只要有一个方法被匹配了，代表这个通知就可以被织入这个bean
     *
     * @return
     */
    private List<Advisor> getMatchedAdvisor(String beanName, Object bean) {
        if (CollectionUtils.isEmpty(advisors)) {
            return null;
        }
        ArrayList<Advisor> matchedAdvisors = new ArrayList<>();
        outer:for (Advisor advisor : this.advisors) {
            if (advisor instanceof PointcutAdvisor) {
                Pointcut pointcut = ((PointcutAdvisor) advisor).getPointcut();
                if (!pointcut.matchClass(bean.getClass())) {
                    continue;
                }

                List<Method> allMethods = getAllMethods(bean);
                for (Method method : allMethods) {
                    if (pointcut.matchMethod(method,bean.getClass())) {
                        matchedAdvisors.add(advisor);
                        continue outer;
                    }
                }

            }
        }
        return matchedAdvisors;
    }

    /**
     * 根据实例获取类的所有方法（包括父类型的，使用Spring框架提供的ReflectionUtils和ClassUtils）
     *
     * @param bean
     * @return
     */
    private List<Method> getAllMethods(Object bean) {
        ArrayList<Method> methods = new ArrayList<>();
        Set<Class<?>> classes = new LinkedHashSet<>(ClassUtils.getAllInterfacesForClassAsSet(bean.getClass()));
        classes.add(bean.getClass());
        for (Class clazz : classes) {
            Method[] declaredMethods = ReflectionUtils.getAllDeclaredMethods(clazz);
            for (Method method : declaredMethods) {
                methods.add(method);
            }
        }
        return methods;
    }

    public Object getProxy(Object bean, String beanName, List<Advisor> matchedAdvisors) throws Exception {
        return AopProxyFactory.getAopProxyFactory().createAopProxy(bean, beanName, matchedAdvisors, this.beanFactory).getProxy();
    }
}
