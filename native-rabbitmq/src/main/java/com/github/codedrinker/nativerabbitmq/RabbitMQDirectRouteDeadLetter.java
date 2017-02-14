package com.github.codedrinker.nativerabbitmq;

import java.io.IOException;

import com.rabbitmq.client.*;

/**
 * Created by codedrinker on 10/02/2017.
 */
public class RabbitMQDirectRouteDeadLetter {
    private static final String QUEUE_NAME = "dead_direct_dead_letter_queue";

    public static void main(String[] argv) throws Exception {
        Connection connection = RabbitMQFactory.getFactory().newConnection();
        final Channel channel = connection.createChannel();
        channel.queueDeclare(QUEUE_NAME, true, false, false, null);
        channel.queueBind(QUEUE_NAME, RabbitMQDirectRouteProducer.DEAD_LETTER_EXCHANGE, RabbitMQDirectRouteProducer.DEAD_LETTER_ROUTE_KEY);
        System.out.println(" [*] Waiting for messages. To exit press CTRL+C");
        Consumer consumer = new DefaultConsumer(channel) {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                String message = new String(body, "UTF-8");
                System.out.println(new RabbitMQDirectRouteDeadLetter().getClass().getCanonicalName() + " Received '" + envelope.getRoutingKey() + "':'" + message + "'" + " at " + System.currentTimeMillis());
//                channel.basicReject(envelope.getDeliveryTag(), true);
                channel.basicAck(envelope.getDeliveryTag(), false);
            }
        };
        channel.basicConsume(QUEUE_NAME, false, consumer);
    }
}