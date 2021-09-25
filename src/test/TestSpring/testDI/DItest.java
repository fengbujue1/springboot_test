package TestSpring.testDI;

import com.zyj.springboot_test.test.spring.IOC.*;
import org.junit.Test;
import TestSpring.testDI.model.MagicGril;
import TestSpring.testDI.model.Renminbi;
import TestSpring.testIOC.Boy;
import TestSpring.testIOC.BoyFactory;
import TestSpring.testIOC.BoyFactoryBean;
import TestSpring.testIOC.Lad;

import java.util.ArrayList;
import java.util.List;

public class DItest {
	static PreBuildBeanFactory bf = new PreBuildBeanFactory();
//	static DefaultBeanFactory bf = new DefaultBeanFactory();

	@Test
	public void testConstructorDI() throws Exception {

		GenericBeanDefinition bd = new GenericBeanDefinition();
		bd.setBeanClass(Lad.class);
		List<Object> args = new ArrayList<>();
		args.add("sunwukong");
		args.add(new BeanReference("magicGril"));
		bd.setParams(args);
//		bd.setScope(BeanDefinition.SINGLETION);
		bf.register("swk", bd);

		bd = new GenericBeanDefinition();
		bd.setBeanClass(MagicGril.class);
		args = new ArrayList<>();
		args.add("baigujing");
		bd.setParams(args);
		bf.register("magicGril", bd);

		bf.preInstantiateSingletons();

		Lad abean = (Lad) bf.getBean("swk");

		abean.sayLove();

		Object magicGril = bf.getBean("magicGril");
		System.out.println(magicGril);
	}

	@Test
	public void test2() throws Exception {
		new Lad("asd", new MagicGril());
	}

	@Test
	public void testStaticFactoryMethodDI() throws Exception {

		GenericBeanDefinition bd = new GenericBeanDefinition();
		bd.setBeanClass(BoyFactory.class);
		bd.setFactoryMethodName("getBoy");
		List<Object> args = new ArrayList<>();
		args.add("niulang");
		args.add(new BeanReference("renmibi"));
		bd.setParams(args);
		bf.register("niulang", bd);

		bd = new GenericBeanDefinition();
		bd.setBeanClass(Renminbi.class);
		bf.register("renmibi", bd);

		bf.preInstantiateSingletons();

		Boy nl = (Boy) bf.getBean("niulang");
		nl.play();
	}

	@Test
	public void testFactoryMethodDI() throws Exception {

		GenericBeanDefinition bd = new GenericBeanDefinition();
		bd.setFactgoryBeanName("boyFactoryBean");
		bd.setFactoryMethodName("getBoy");
		List<Object> args = new ArrayList<>();
		args.add("猪八戒");
		args.add(new BeanReference("xiaolongnu"));
		bd.setParams(args);
		bf.register("zhubajie", bd);

		bd = new GenericBeanDefinition();
		bd.setBeanClass(BoyFactoryBean.class);
		bf.register("boyFactoryBean", bd);

		bd = new GenericBeanDefinition();
		bd.setBeanClass(MagicGril.class);
		bf.register("xiaolongnu", bd);

		bf.preInstantiateSingletons();

		Boy abean = (Boy) bf.getBean("zhubajie");

		abean.sayLove();
	}

	@Test
	public void testChildTypeDI() throws Exception {

		GenericBeanDefinition bd = new GenericBeanDefinition();
		bd.setBeanClass(Lad.class);
		List<Object> args = new ArrayList<>();
		args.add("niulang");
		args.add(new BeanReference("zhinv"));
		bd.setParams(args);
		bf.register("nl", bd);

		bd = new GenericBeanDefinition();
		bd.setBeanClass(MagicGril.class);
		args = new ArrayList<>();
		args.add("zhinv");
		bd.setParams(args);
		bf.register("zhinv", bd);

		bf.preInstantiateSingletons();

		Boy abean = (Boy) bf.getBean("nl");

		abean.sayLove();
	}
}
