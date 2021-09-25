package TestSpring.testIOC;


import com.zyj.springboot_test.test.spring.IOC.BeanDefinition;
import com.zyj.springboot_test.test.spring.IOC.DefaultBeanFactory;
import com.zyj.springboot_test.test.spring.IOC.GenericBeanDefinition;
import org.junit.AfterClass;
import org.junit.Test;

public class DefaultBeanFactoryTest {
    static DefaultBeanFactory bf = new DefaultBeanFactory();
    @Test
    public void testRegist() throws Exception {

        GenericBeanDefinition bd = new GenericBeanDefinition();

        bd.setBeanClass(Lad.class);
        bd.setScope(BeanDefinition.SINGLETION);
        // bd.setScope(BeanDefinition.SCOPE_PROTOTYPE);

        bd.setInitMethod("init");
        bd.setDestroyMethod("destroy");

        bf.register("lad", bd);

    }

    @Test
    public void testRegistStaticFactoryMethod() throws Exception {
        GenericBeanDefinition bd = new GenericBeanDefinition();
        bd.setBeanClass(BoyFactory.class);
        bd.setFactoryMethodName("getBoy");
        bd.setScope(BeanDefinition.SINGLETION);
        bf.register("staticBoyFactory", bd);
    }

    @Test
    public void testRegistFactoryMethod() throws Exception {
        GenericBeanDefinition bd = new GenericBeanDefinition();
        bd.setBeanClass(BoyFactoryBean.class);
        String fbname = "getBoy";
        bf.register(fbname, bd);

        bd = new GenericBeanDefinition();
        bd.setFactgoryBeanName(fbname);
        bd.setFactoryMethodName("getBoy");
        bd.setScope(BeanDefinition.PROTOTYPE);

        bf.register("factoryBoy", bd);
    }

    @AfterClass
    public static void testGetBean() throws Exception {
        System.out.println("构造方法方式------------");
        for (int i = 0; i < 3; i++) {
            Boy boy = (Boy) bf.getBean("lad");
            boy.sayLove();
        }

        System.out.println("静态工厂方法方式------------");
        for (int i = 0; i < 3; i++) {
            Boy ab = (Boy) bf.getBean("staticBoyFactory");
            ab.sayLove();
        }

        System.out.println("工厂方法方式------------");
        for (int i = 0; i < 3; i++) {
            Boy ab = (Boy) bf.getBean("factoryBoy");
            ab.sayLove();
        }

        bf.close();
    }
}
