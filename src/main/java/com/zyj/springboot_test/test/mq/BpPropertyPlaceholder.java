package com.zyj.springboot_test.test.mq;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;

import java.util.Enumeration;
import java.util.Properties;

/**
 * <p>
 * BP配置文件加载预处理（spring加载的properties文件）
 */
public class BpPropertyPlaceholder extends PropertyPlaceholderConfigurer
{
	@Override
	protected void processProperties(ConfigurableListableBeanFactory beanFactoryToProcess, Properties props)
			throws BeansException {
		Enumeration<?> keys = props.propertyNames();
		while (keys.hasMoreElements()) {
			String key = (String) keys.nextElement();
			String value = props.getProperty(key);
			props.remove(key);
			props.setProperty(key, value);
		}
		super.processProperties(beanFactoryToProcess, props);
	}
}
