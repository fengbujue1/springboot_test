package com.zyj.springboot_test.test.spring.AOP.advisor;

import com.zyj.springboot_test.test.spring.AOP.pointcut.Pointcut;

/**
 * 基于切入点的通知者
 */
public interface PointcutAdvisor extends Advisor {
    Pointcut getPointcut();
}
