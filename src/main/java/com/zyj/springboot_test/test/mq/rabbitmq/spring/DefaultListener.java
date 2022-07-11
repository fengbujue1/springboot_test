package com.zyj.springboot_test.test.mq.rabbitmq.spring;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageListener;

/**
 *
 * @author 周赟吉
 * @since 2021/12/2
 */
public class DefaultListener implements MessageListener {
    @Override
    public void onMessage(Message message) {
        System.out.println(new String(message.getBody()));
    }

}
