package TestSpring.testAop;

import com.zyj.springboot_test.bean.HelloBody;
import org.aspectj.weaver.tools.PointcutExpression;
import org.aspectj.weaver.tools.PointcutParser;
import org.aspectj.weaver.tools.ShadowMatch;
import org.junit.Test;

import java.lang.reflect.Method;

public class TestAspectJ {
    @Test
    public void test() throws NoSuchMethodException {
        //获取解析器
        PointcutParser pp = PointcutParser.
                getPointcutParserSupportingAllPrimitivesAndUsingContextClassloaderForResolution();
        //获取切入点表达式
        PointcutExpression pe = pp.parsePointcutExpression("execution(* com.zyj.springboot_test.bean.HelloBody.*Hello(..))");

        //根据类进行匹配（不可靠）
        System.out.println(pe.couldMatchJoinPointsInType(HelloBody.class));

        Class<HelloBody> helloBodyClass = HelloBody.class;
        Method doSomething1 = helloBodyClass.getDeclaredMethod("doSomething1", null);
        Method setHello = helloBodyClass.getDeclaredMethod("setHello", String.class);
        Method getHello = helloBodyClass.getDeclaredMethod("getHello", null);

        ShadowMatch shadowMatch = pe.matchesMethodExecution(doSomething1);
        ShadowMatch shadowMatch1 = pe.matchesMethodExecution(setHello);
        ShadowMatch shadowMatch2 = pe.matchesMethodExecution(getHello);
        //不能匹配指定类的非指定方法
        System.out.println(shadowMatch.alwaysMatches());
        //可以匹配到指定类的指定方法
        System.out.println(shadowMatch1.alwaysMatches());
        //可以匹配到指定类的指定方法
        System.out.println(shadowMatch2.alwaysMatches());

    }
}
