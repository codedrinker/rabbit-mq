package com.github.codedrinker.nativerabbitmq;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

/**
 * Created by codedrinker on 08/02/2017.
 */
public class RabbitMQBasicProducer {
    private final static String QUEUE_NAME = "test1";

    public static void main(String[] argv) throws Exception {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        factory.setUsername("admin");
        factory.setPassword("admin");
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();
        channel.queueDeclare(QUEUE_NAME, true, false, false, null);
        String message = "Hello World! ";
        int i = 10;
        while (true) {
            channel.basicPublish("", QUEUE_NAME, new AMQP.BasicProperties(), (message + i).getBytes("UTF-8"));
            System.out.println(("send : " + message + i));
            Thread.sleep(10000);
            i++;
        }
    }
}
