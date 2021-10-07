package com.zyj.springboot_test.test.spring.IOC;

/**
 * bean工厂构建通知接口
 */
public interface BeanFactoryAware extends Aware {
	/**
	 * 接口实现者获得bean工厂方法
	 * @param bf
	 */
	void setBeanFactory(BeanFactory bf);
}
