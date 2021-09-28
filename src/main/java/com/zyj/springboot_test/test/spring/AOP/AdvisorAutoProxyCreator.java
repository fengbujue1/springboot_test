package com.zyj.springboot_test.test.spring.AOP;

import com.zyj.springboot_test.test.spring.AOP.advisor.Advisor;
import com.zyj.springboot_test.test.spring.AOP.advisor.AdvisorRegister;
import com.zyj.springboot_test.test.spring.AOP.advisor.PointcutAdvisor;
import com.zyj.springboot_test.test.spring.AOP.pointcut.Pointcut;
import com.zyj.springboot_test.test.spring.IOC.BeanPostProcessor;
import jodd.util.CollectionUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.util.ClassUtils;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Method;
import java.util.*;

public class AdvisorAutoProxyCreator implements BeanPostProcessor, AdvisorRegister {

    private List<Advisor> advisors;
    @Override
    public void registorAdvistor(Advisor advisor) {
        advisors.add(advisor);
    }

    @Override
    public List<Advisor> getAdvistors() {
        return advisors;
    }

    @Override
    public void postProcessBeforeInitialization(Object bean, String beanName) {
        List<Advisor> matchedAdvisor =
                getMatchedAdvisor(beanName, bean);
        //TODO 实际的代理增强

    }

    @Override
    public void postProcessAfterInitialization(Object bean, String beanName) {
        //TODO 实际的代理增强
    }

    /**
     * 获取匹配的通知
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
                    if (pointcut.matchMethod(method)) {
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
        Set<Class<?>> classes = ClassUtils.getAllInterfacesForClassAsSet(bean.getClass());
        for (Class clazz : classes) {
            Method[] declaredMethods = ReflectionUtils.getAllDeclaredMethods(clazz);
            for (Method method : declaredMethods) {
                methods.add(method);
            }
        }
        return methods;
    }
}
