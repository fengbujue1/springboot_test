package com.zyj.springboot_test.test.mq.rabbitmq.original;

import com.rabbitmq.client.*;

import java.io.IOException;

/**
 * @Author 周赟吉
 * @Date 2022/7/10 11:16
 * @Description :
 */
public class Consumer {
    public static void main(String[] args) throws Exception{
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

        //4.创建消费者
        String queueName = "rmqtest";
        channel.basicConsume(queueName, true, new com.rabbitmq.client.Consumer() {
            @Override
            public void handleConsumeOk(String s) {
                System.out.println(s);
            }

            @Override
            public void handleCancelOk(String s) {
                System.out.println(s);
            }

            @Override
            public void handleCancel(String s) throws IOException {
                System.out.println(s);
            }

            @Override
            public void handleShutdownSignal(String s, ShutdownSignalException e) {
                System.out.println(s);

            }

            @Override
            public void handleRecoverOk(String s) {
                System.out.println(s);

            }

            @Override
            public void handleDelivery(String s, Envelope envelope, AMQP.BasicProperties basicProperties, byte[] bytes) throws IOException {
                System.out.println(new String(bytes));

            }
        });

        while (true) {

        }
    }
}
