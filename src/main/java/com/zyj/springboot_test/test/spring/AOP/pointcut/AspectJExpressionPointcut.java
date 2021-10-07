package com.zyj.springboot_test.test.spring.AOP.pointcut;

import org.aspectj.weaver.tools.PointcutExpression;
import org.aspectj.weaver.tools.PointcutParser;
import org.aspectj.weaver.tools.ShadowMatch;

import java.lang.reflect.Method;

/**
 * 基于 AspectJ 的切点实现
 */
public class AspectJExpressionPointcut implements Pointcut {
    String expression;
    //获取AspectJ 表达式匹配器
    PointcutParser pp = PointcutParser.
            getPointcutParserSupportingAllPrimitivesAndUsingContextClassloaderForResolution();
    PointcutExpression pe = null;

    public AspectJExpressionPointcut(String expression) {
        this.expression = expression;
        pe = pp.parsePointcutExpression(expression);
    }

    @Override
    public boolean matchClass(Class<?> cls) {
        return pe.couldMatchJoinPointsInType(cls);
    }

    @Override
    public boolean matchMethod(Method method, Class<?> targetClass) {
        ShadowMatch shadowMatch = pe.matchesMethodExecution(method);
        return shadowMatch.alwaysMatches();
    }

    public String getExpression() {
        return expression;
    }
}
