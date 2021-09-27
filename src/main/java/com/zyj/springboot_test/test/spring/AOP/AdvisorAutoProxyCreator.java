package com.zyj.springboot_test.test.spring.AOP;

import com.zyj.springboot_test.test.spring.AOP.advisor.Advisor;
import com.zyj.springboot_test.test.spring.AOP.advisor.AdvisorRegister;
import com.zyj.springboot_test.test.spring.AOP.advisor.PointcutAdvisor;
import com.zyj.springboot_test.test.spring.AOP.pointcut.Pointcut;
import com.zyj.springboot_test.test.spring.IOC.BeanPostProcessor;

import java.util.List;

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
        getMatchedAdvisor(beanName, bean);
        //TODO 实际的代理增强
        for (Advisor advisor : advisors) {
            if (advisor instanceof PointcutAdvisor) {
                Pointcut pointcut =((PointcutAdvisor) advisor).getPointcut();
                if (!pointcut.matchClass(bean.getClass())) {
                    continue;
                }

                if () {
                }
            }
        }
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
        if (advisors==null||advisors) {
        }
    }
}
