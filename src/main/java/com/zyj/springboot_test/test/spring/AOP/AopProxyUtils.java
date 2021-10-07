package com.zyj.springboot_test.test.spring.AOP;

import com.zyj.springboot_test.test.spring.AOP.advisor.Advisor;
import com.zyj.springboot_test.test.spring.AOP.advisor.PointcutAdvisor;
import com.zyj.springboot_test.test.spring.IOC.BeanFactory;
import org.springframework.util.CollectionUtils;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class AopProxyUtils {
    public static Object applyAdvices(Object target, Method method, Object[] args, List<Advisor> matchAdvisors, Object proxy, BeanFactory beanFactory) throws Throwable {
        // 这里要做什么？
        // 1、获取要对当前方法进行增强的advice Bean列表
        List<Object> advisorsToSpecificMethod = getAdvisorsToSpecificMethod(target.getClass(), method, matchAdvisors, beanFactory);

        // 2、如有增强的advice，责任链式增强执行
        if (CollectionUtils.isEmpty(advisorsToSpecificMethod)) {
            return method.invoke(target, args);
        } else {
            // 责任链式执行增强
            AopAdviceChainInvocation chain = new AopAdviceChainInvocation(advisorsToSpecificMethod, method, target, args);
            return chain.invoke();
        }
    }

    /**
     * 针对某个具体的方法进行匹配具体的通知
     */
    public static List<Object> getAdvisorsToSpecificMethod(Class<?> beanClass, Method method, List<Advisor> matchAdvisors, BeanFactory beanFactory) throws Exception {
        if (CollectionUtils.isEmpty(matchAdvisors)) {
            return null;
        }
        List<Object> advices = new ArrayList<>();
        for (Advisor ad : matchAdvisors) {
            if (ad instanceof PointcutAdvisor) {
                if (((PointcutAdvisor) ad).getPointcut().matchMethod(method, beanClass)) {
                    advices.add(beanFactory.getBean(ad.getAdviceBeanName()));
                }
            }
        }
        return advices;

    }
}
