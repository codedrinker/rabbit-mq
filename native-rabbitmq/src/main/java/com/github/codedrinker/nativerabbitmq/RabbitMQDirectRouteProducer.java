package com.github.codedrinker.nativerabbitmq;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;

/**
 * Created by codedrinker on 10/02/2017.
 */
public class RabbitMQDirectRouteProducer {

    public static final String EXCHANGE_NAME = "direct_exchange";
    public static final String DEAD_LETTER_EXCHANGE = "dead_letter_exchange";
    public static final String DEAD_LETTER_ROUTE_KEY = "dead_letter_route_key";

    public static void main(String[] argv) throws Exception {
        Connection connection = RabbitMQFactory.getFactory().newConnection();
        Channel channel = connection.createChannel();
        channel.exchangeDeclare(EXCHANGE_NAME, "direct", true);
        channel.exchangeDeclare(DEAD_LETTER_EXCHANGE, "direct", true);
        int i = 0;
        while (true) {
            String severity = i % 3 == 0 ? "info" : "error";
            if (i % 7 == 0) {
                severity = "debug";
            }
            String message = "This is message";
            channel.basicPublish(EXCHANGE_NAME,
                    severity,
                    new AMQP.BasicProperties.Builder().deliveryMode(2).expiration("60000").priority(1).build(),
                    (message + " : " + i).getBytes("UTF-8"));
            System.out.println(new RabbitMQDirectRouteProducer().getClass().getCanonicalName() + " Sent '" + severity + "':'" + (message + " : " + i) + "' " + System.currentTimeMillis());
            Thread.sleep(10000);
            i++;
        }
    }
}