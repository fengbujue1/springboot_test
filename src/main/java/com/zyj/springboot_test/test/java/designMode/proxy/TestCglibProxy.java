package com.zyj.springboot_test.test.java.designMode.proxy;

import com.zyj.springboot_test.bean.HelloBody;
import org.springframework.cglib.proxy.*;

import java.lang.reflect.Method;

public class TestCglibProxy {
    public static void main(String args[]) {
        HelloBody helloBody = new HelloBody();
        Enhancer enhancer = new Enhancer();
        MethodInterceptor1 methodInterceptor1 = new MethodInterceptor1(helloBody);
        MethodInterceptor2 methodInterceptor2 = new MethodInterceptor2(helloBody);
        Callback[] callbacks = {methodInterceptor1, methodInterceptor2};
//        enhancer.setCallbacks(callbacks);
//        enhancer.setCallbackFilter(new MyCallbackFilter());
        enhancer.setCallback(methodInterceptor1);
        enhancer.setSuperclass(HelloBody.class);
        enhancer.setInterfaces(null);
        HelloBody proxy = (HelloBody) enhancer.create();
        proxy.doSomething1();
        proxy.doSomething2();
    }


}
class MethodInterceptor1 implements MethodInterceptor {
    private HelloBody target;

    public MethodInterceptor1(HelloBody target) {
        this.target = target;
    }

    @Override
    public Object intercept(Object proxy, Method method, Object[] args, MethodProxy methodProxy) throws Throwable {

        Object ret = null;

        //调用父类的该方法，即不产生额外增强行为
//        ret = methodProxy.invokeSuper(proxy, args);
        System.out.println("before");
        if (target != null) {
            ret = method.invoke(target, args);
        }

        return ret;
    }
}
class MethodInterceptor2 implements MethodInterceptor {
    private HelloBody target;

    public MethodInterceptor2(HelloBody target) {
        this.target = target;
    }

    @Override
    public Object intercept(Object proxy, Method method, Object[] args, MethodProxy methodProxy) throws Throwable {

        Object ret = null;

        //调用父类的该方法，即不产生额外增强行为
//        ret = methodProxy.invokeSuper(proxy, args);
        System.out.println("before2");
        if (target != null) {
            ret = method.invoke(target, args);
        }

        return ret;
    }
}
class MyCallbackFilter implements CallbackFilter {
    @Override
    public int accept(Method method) {
        String name = method.getName();
        if ("doSomething1".equals(name)) {
            return 0;
        } else if ("doSomething2".equals(name)) {
            return 1;
        } else {
            return 0;
        }
    }
}
