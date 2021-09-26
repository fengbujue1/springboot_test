package com.zyj.springboot_test.test.spring.AOP.advisor;

import com.zyj.springboot_test.test.spring.AOP.pointcut.AspectJExpressionPointcut;
import com.zyj.springboot_test.test.spring.AOP.pointcut.Pointcut;

/**
 * 基于AspectJ的 通知者
 */
public class AspectJPointcutAdvisor extends AbstractPointcutAdvisor {

    public AspectJPointcutAdvisor(String adviceBeanName, String expression) {
        this.adviceBeanName = adviceBeanName;
        this.expression = expression;
        this.pointcut = new AspectJExpressionPointcut(expression);
    }

    @Override
    public Pointcut getPointcut() {
        return super.getPointcut();
    }
}
