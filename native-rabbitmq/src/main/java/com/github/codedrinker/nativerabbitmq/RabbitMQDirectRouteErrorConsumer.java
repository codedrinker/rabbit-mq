package com.github.codedrinker.nativerabbitmq;

import com.rabbitmq.client.*;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by codedrinker on 10/02/2017.
 */
public class RabbitMQDirectRouteErrorConsumer {
    public static final String QUEUE_NAME = "log.error.queue";

    public static void main(String[] argv) throws Exception {
        Connection connection = RabbitMQFactory.getFactory().newConnection();
        final Channel channel = connection.createChannel();
        channel.exchangeDeclare(RabbitMQDirectRouteProducer.EXCHANGE_NAME, "direct", true);
        Map args = new HashMap();
        args.put("x-message-ttl", 60000);
        args.put("x-dead-letter-exchange", RabbitMQDirectRouteProducer.DEAD_LETTER_EXCHANGE);
        args.put("x-dead-letter-routing-key", RabbitMQDirectRouteProducer.DEAD_LETTER_ROUTE_KEY);
        channel.queueDeclare(QUEUE_NAME, true, false, false, args);
        channel.queueBind(QUEUE_NAME, RabbitMQDirectRouteProducer.EXCHANGE_NAME, "error");
        System.out.println(" [*] Waiting for messages. To exit press CTRL+C");
        Consumer consumer = new DefaultConsumer(channel) {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                String message = new String(body, "UTF-8");
                System.out.println(new RabbitMQDirectRouteErrorConsumer().getClass().getCanonicalName() + " Received '" + envelope.getRoutingKey() + "':'" + message + "'" + " at " + System.currentTimeMillis());
                channel.basicReject(envelope.getDeliveryTag(), false);
//                channel.basicAck(envelope.getDeliveryTag(), false);
            }
        };
        channel.basicConsume(QUEUE_NAME, false, consumer);
    }
}