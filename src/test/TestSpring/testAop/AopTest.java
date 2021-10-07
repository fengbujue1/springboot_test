package TestSpring.testAop;

import TestSpring.testDI.model.MagicGril;
import TestSpring.testDI.model.Renminbi;
import TestSpring.testIOC.Boy;
import TestSpring.testIOC.Lad;
import com.zyj.springboot_test.test.spring.AOP.AdvisorAutoProxyCreator;
import com.zyj.springboot_test.test.spring.AOP.advisor.AspectJPointcutAdvisor;
import com.zyj.springboot_test.test.spring.IOC.BeanReference;
import com.zyj.springboot_test.test.spring.IOC.GenericBeanDefinition;
import com.zyj.springboot_test.test.spring.IOC.PreBuildBeanFactory;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class AopTest {

    static PreBuildBeanFactory bf = new PreBuildBeanFactory();

    @Test
    public void testCirculationDI() throws Throwable {

        GenericBeanDefinition bd = new GenericBeanDefinition();
        bd.setBeanClass(Lad.class);
        List<Object> args = new ArrayList<>();
        args.add("sunwukong");
        args.add(new BeanReference("baigujing"));
        bd.setParams(args);
        bf.register("swk", bd);

        bd = new GenericBeanDefinition();
        bd.setBeanClass(MagicGril.class);
        args = new ArrayList<>();
        args.add("baigujing");
        bd.setParams(args);
        bf.register("baigujing", bd);

        bd = new GenericBeanDefinition();
        bd.setBeanClass(Renminbi.class);
        bf.register("renminbi", bd);

        // 前置增强advice bean注册
        bd = new GenericBeanDefinition();
        bd.setBeanClass(MyBeforeAdvice.class);
        bf.register("myBeforeAdvice", bd);

        // 环绕增强advice bean注册
        bd = new GenericBeanDefinition();
        bd.setBeanClass(MyMethodInterceptor.class);
        bf.register("myMethodInterceptor", bd);

        // 后置增强advice bean注册
        bd = new GenericBeanDefinition();
        bd.setBeanClass(MyAfterReturningAdvice.class);
        bf.register("myAfterReturningAdvice", bd);

        // 往BeanFactory中注册AOP的BeanPostProcessor
        AdvisorAutoProxyCreator aapc = new AdvisorAutoProxyCreator();
        bf.registerBeanPostProcessor(aapc);
        // 向AdvisorAutoProxyCreator注册Advisor
        aapc.registorAdvistor(
                new AspectJPointcutAdvisor("myBeforeAdvice", "execution(* TestSpring.testDI.model.MagicGril.*(..))"));
        // 向AdvisorAutoProxyCreator注册Advisor
        aapc.registorAdvistor(
                new AspectJPointcutAdvisor("myMethodInterceptor", "execution(* TestSpring.testIOC.Lad.*(..))"));
        // 向AdvisorAutoProxyCreator注册Advisor
        aapc.registorAdvistor(new AspectJPointcutAdvisor("myAfterReturningAdvice",
                "execution(* TestSpring.testDI.model.Renminbi.*(..))"));

        bf.preInstantiateSingletons();

        System.out.println("-----------------myBeforeAdvice---------------");
        MagicGril gril = (MagicGril) bf.getBean("baigujing");
        gril.getFriend();
        gril.getName();
        
        System.out.println("----------------myMethodInterceptor----------------");
        Boy boy = (Boy) bf.getBean("swk");
        boy.sayLove();
        
        System.out.println("-----------------myAfterReturningAdvice---------------");
        Renminbi rmb = (Renminbi) bf.getBean("renminbi");
        rmb.pay();
    }
}
