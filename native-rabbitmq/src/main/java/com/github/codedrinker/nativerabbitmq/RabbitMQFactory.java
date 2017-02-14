package com.github.codedrinker.nativerabbitmq;

import com.rabbitmq.client.ConnectionFactory;

/**
 * Created by codedrinker on 10/02/2017.
 */
public class RabbitMQFactory {
    private static ConnectionFactory factory;

    private RabbitMQFactory() {
    }

    public static ConnectionFactory getFactory() {
        if (factory == null) {
            factory = new ConnectionFactory();
            factory.setHost("localhost");
            factory.setUsername("admin");
            factory.setPassword("admin");
        }
        return factory;
    }
}
