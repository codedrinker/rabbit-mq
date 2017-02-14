package com.github.codedrinker.nativerabbitmq;

import com.rabbitmq.client.*;

import java.io.IOException;

/**
 * Created by codedrinker on 08/02/2017.
 */
public class RabbitMQBasicConsumer {

    private final static String QUEUE_NAME = "test1";

    public static void main(String[] args) throws Exception {
        final ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        factory.setUsername("admin");
        factory.setPassword("admin");
        Connection connection = factory.newConnection();
        final Channel channel = connection.createChannel();

        channel.queueDeclare(QUEUE_NAME, true, false, false, null);
        System.out.println(" [*] Waiting for messages. To exit press CTRL+C");
        Consumer consumer = new DefaultConsumer(channel) {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body)
                    throws IOException {
                String message = new String(body, "UTF-8");
                System.out.println(" [x] Received '" + message + "'");
                System.out.println(envelope.getDeliveryTag());
                if (envelope.isRedeliver()) {
                    channel.basicReject(envelope.getDeliveryTag(), true);
                }
                channel.basicNack(envelope.getDeliveryTag(), false, true);
//                channel.basicAck(envelope.getDeliveryTag(),true);
            }
        };
        channel.basicConsume(QUEUE_NAME, false, consumer);
    }
}

