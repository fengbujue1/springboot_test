package com.zyj.springboot_test.test.mq.rabbitmq.original;

import com.rabbitmq.client.*;

import java.io.IOException;

/**
 * @Author 周赟吉
 * @Date 2022/7/13 10:30
 * @Description :
 */
public class Producer_return {
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
        channel.exchangeDeclare(exchange, "direct", true,false,null);
        channel.queueDeclare(queueName, true, false, false, null);
        channel.queueBind(queueName, exchange, routingKey);
        //6.先添加一个return的监听器
        System.out.println("add return listener");
        channel.addReturnListener(new ReturnListener() {
            @Override
            public void handleReturn(int i, String s, String s1, String s2, AMQP.BasicProperties basicProperties, byte[] bytes) throws IOException {
                System.out.println("s:"+s);
                System.out.println("s1:"+s1);
                System.out.println("s2:"+s2);
                System.out.println("bytes:"+new String(bytes));
            }
        });
        //7.发送一条消息

        String msg = "Hello world";
//        channel.basicPublish(exchange, routingKey,true, null, msg.getBytes());
//        第三个参数Mandatory为true会接收到返回，为false，broker就会删除消息，不会返回
//        channel.basicPublish(exchange, "routingKey",true, null, msg.getBytes());
        channel.basicPublish(exchange, "routingKey",false, null, msg.getBytes());

       Thread.sleep(10);





    }
}
