package com.zyj.springboot_test.test.spring.AOP;

import com.zyj.springboot_test.test.spring.AOP.advice.AfterReturningAdvice;
import com.zyj.springboot_test.test.spring.AOP.advice.MethodBeforeAdvice;
import com.zyj.springboot_test.test.spring.AOP.advice.MethodInterceptor;
import com.zyj.springboot_test.test.spring.AOP.advisor.Advisor;
import com.zyj.springboot_test.test.spring.IOC.BeanFactory;

import java.lang.reflect.Method;
import java.util.List;

public class AopAdviceChainInvocation {

    private static Method invokeMethod;
    static {
        try {
            invokeMethod = AopAdviceChainInvocation.class.getMethod("invoke", null);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
    }
    private List<Object> advices;
    private Method method;
    private Object target;
    private Object[] args;

    public AopAdviceChainInvocation(List<Object> advices, Method method, Object target, Object[] args) {
        this.advices = advices;
        this.method = method;
        this.target = target;
        this.args = args;
    }

    int i = 0;


    //责任链模式，递归调用进行增强
    public Object invoke() throws Throwable {
        if (i < advices.size()) {
            Object advice = advices.get(i++);
            //前置增强
            if (advice instanceof MethodBeforeAdvice) {
                ((MethodBeforeAdvice) advice).before(method, args, target);
            } else if (advice instanceof AfterReturningAdvice) {
                //后置增强
                Object returnVal = invoke();
                ((AfterReturningAdvice) advice).afterReturning(returnVal,method, args, target);
                return returnVal;
            } else if (advice instanceof MethodInterceptor) {
                //环绕增强.注意这里给入的method 和 对象 是invoke方法和链对象
                return ((MethodInterceptor) advice).invoke(invokeMethod, null, this);
            }
            return this.invoke();
        } else{
            return method.invoke(target, args);
        }
    }

}
