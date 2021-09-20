package testDI;

import com.zyj.springboot_test.test.spring.IOC.BeanReference;
import com.zyj.springboot_test.test.spring.IOC.GenericBeanDefinition;
import com.zyj.springboot_test.test.spring.IOC.PreBuildBeanFactory;
import com.zyj.springboot_test.test.spring.IOC.PropertyValue;
import org.junit.Test;
import testDI.model.MagicGril;
import testIOC.Lad;

import java.util.ArrayList;
import java.util.List;

public class PropertyDItest {
	static PreBuildBeanFactory bf = new PreBuildBeanFactory();

	@Test
	public void testPropertyDI() throws Exception {

		GenericBeanDefinition bd = new GenericBeanDefinition();
		bd.setBeanClass(Lad.class);
		List<Object> args = new ArrayList<>();
		args.add("孙悟空");
		args.add(new BeanReference("bgj"));
		bd.setParams(args);
		bf.register("swk", bd);

		bd = new GenericBeanDefinition();
		bd.setBeanClass(MagicGril.class);
		List<PropertyValue> propertyValues = new ArrayList<>();
		propertyValues.add(new PropertyValue("name", "白骨精"));
		propertyValues.add(new PropertyValue("friend", new BeanReference("swk")));
		bd.setPropertiesValues(propertyValues);
		bf.register("bgj", bd);

		bf.preInstantiateSingletons();

		MagicGril g = (MagicGril) bf.getBean("bgj");
		System.out.println(g.getName() + " " + g.getFriend());
		g.getFriend().sayLove();
	}

}
