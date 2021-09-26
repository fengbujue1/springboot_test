package com.zyj.springboot_test.test.spring.AOP.advisor;

/**
 * 通知者：使用外观模式，让aop框架的使用者可以更方便的使用aop
 *只需要两个元素  String类型的 adviceBeanName 和 String类型的 切入点表达式 expression
 */
public interface Advisor {
    String getAdviceBeanName();
    String getExpression();
}
