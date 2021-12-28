package com.zyj.springboot_test.test.mq;

import com.zyj.springboot_test.util.PropertiesUtil;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.Scanner;

/**
 *
 * @author 周赟吉
 * @since 2021/12/21
 */
public class ProducerMain {
    public static void main(String[] args) {
        String contextFileName = "rabbitmq/bpt-context-rabbitmq.xml";
        String propertyName = "rabbitmq/mq.properties";
        PropertiesUtil.loadPropertiesFromFile(propertyName);
        ApplicationContext context = new ClassPathXmlApplicationContext(contextFileName);
        RabbitTemplate rabbitTemplate = (RabbitTemplate) context.getBean("rabbitTemplate");
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("输入消息：");
            String s = scanner.nextLine();
            rabbitTemplate.convertAndSend(s);
        }
    }
}
