package com.zyj.springboot_test.test.mq.rabbitmq.original;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.ConfirmListener;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * @Author 周赟吉
 * @Date 2022/7/10 10:30
 * @Description :
 */
public class Producer {
    public static void main(String[] args) throws Exception {
        //1.创建连接工厂
        ConnectionFactory connectionFactory = new ConnectionFactory();
        connectionFactory.setHost("47.96.0.106");
        connectionFactory.setPort(5672);
        connectionFactory.setVirtualHost("/");
        connectionFactory.setUsername("bujue");
        connectionFactory.setPassword("123");

        //2.获取connectionn
        Connection connection = connectionFactory.newConnection();

        //3.获取channel
        Channel channel = connection.createChannel();

        //4.指定消息投递模式：确认模式
        channel.confirmSelect();

        //5.声明交换机 和队列，进行绑定，然后指令路由键
        String exchange = "exchange2";
        String routingKey = "rmqtest";
        String queueName = "rmqtest";
        channel.exchangeDeclare(exchange, "direct", true);
        channel.queueDeclare(queueName, true, false, false, null);
        channel.queueBind(queueName, exchange, routingKey);

        //6.发送一条消息

        String msg = "Hello world";
        channel.basicPublish(exchange, routingKey, null, msg.getBytes());

       Thread.sleep(10);


        //6.添加一个消息确认的监听器
        System.out.println("aad confirm listener");
         channel.addConfirmListener(new ConfirmListener() {
             @Override
             public void handleAck(long l, boolean b) throws IOException {
                 System.out.println(Thread.currentThread().getName()+"ack");
             }

             @Override
             public void handleNack(long l, boolean b) throws IOException {
                 System.out.println(Thread.currentThread().getName()+"nack");
             }
         });


    }
}
