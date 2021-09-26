package com.zyj.springboot_test.test.spring.AOP.advisor;

import com.zyj.springboot_test.test.spring.AOP.pointcut.Pointcut;

/**
 * 抽象的 带切入点的通知者实现
 */
public class AbstractPointcutAdvisor implements PointcutAdvisor {
    String adviceBeanName;
    String expression;
    Pointcut pointcut;


    @Override
    public Pointcut getPointcut() {
        return pointcut;
    }

    @Override
    public String getAdviceBeanName() {
        return adviceBeanName;
    }

    @Override
    public String getExpression() {
        return expression;
    }
}
