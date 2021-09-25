package TestSpring.testDI;

import com.zyj.springboot_test.test.spring.IOC.BeanReference;
import com.zyj.springboot_test.test.spring.IOC.GenericBeanDefinition;
import com.zyj.springboot_test.test.spring.IOC.PreBuildBeanFactory;
import org.junit.Test;
import TestSpring.testDI.model.Niulang;
import TestSpring.testDI.model.Zhinv;

import java.util.ArrayList;
import java.util.List;

public class CirculationDiTest {

	static PreBuildBeanFactory bf = new PreBuildBeanFactory();

	@Test
	public void testCirculationDI() throws Exception {
		GenericBeanDefinition bd = new GenericBeanDefinition();
		bd.setBeanClass(Niulang.class);
		List<Object> args = new ArrayList<>();
		args.add("nulang");
		args.add(new BeanReference("zhinv"));
		bd.setParams(args);
		bf.register("nl", bd);

		bd = new GenericBeanDefinition();
		bd.setBeanClass(Zhinv.class);
		args = new ArrayList<>();
		args.add(new BeanReference("nl"));
		bd.setParams(args);
		bf.register("zhinv", bd);

		bf.preInstantiateSingletons();
	}
}
