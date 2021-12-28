package com.zyj.springboot_test.test.mq;

import com.zyj.springboot_test.util.PropertiesUtil;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Description:
 * 参考文档归档于：http://192.168.0.2/svn/doc/Doc.Dev/BP/开发备忘/Wiki/交易报文规范/TX0022-跨境人民币支付（CIPS）.xlsx
 *
 * @author 周赟吉
 * @since 2021/12/2
 */
public class ConsumerMain {
    public static void main(String[] args) {
        String contextFileName = "rabbitmq/bpt-context-rabbitmq.xml";
        String propertyName = "rabbitmq/mq.properties";
        PropertiesUtil.loadPropertiesFromFile(propertyName);
        ApplicationContext context = new ClassPathXmlApplicationContext(contextFileName);
        SimpleMessageListenerContainer container = (SimpleMessageListenerContainer) context.getBean("simpleMessageListenerContainer");
        container.start();
    }
}
