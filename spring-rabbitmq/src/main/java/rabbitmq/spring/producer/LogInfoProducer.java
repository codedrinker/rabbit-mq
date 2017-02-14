package rabbitmq.spring.producer;

import org.springframework.stereotype.Component;

/**
 * Created by codedrinker on 13/02/2017.
 */
@Component
public class LogInfoProducer extends LogProducer {
    @Override
    protected String routeKey() {
        return "info";
    }
}