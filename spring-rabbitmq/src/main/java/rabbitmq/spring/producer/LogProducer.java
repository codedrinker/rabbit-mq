package rabbitmq.spring.producer;

import com.alibaba.fastjson.JSON;
import rabbitmq.spring.entity.Log;
import org.springframework.amqp.core.*;

import javax.annotation.Resource;
import java.util.UUID;

/**
 * Created by codedrinker on 13/02/2017.
 */
public abstract class LogProducer {
    @Resource(name = "logTemplate")
    AmqpTemplate amqpTemplate;

    protected abstract String routeKey();

    public void produce(Log log) {
        try {
            Message message = MessageBuilder.withBody(JSON.toJSONString(log).getBytes())
                    .setContentType(MessageProperties.CONTENT_TYPE_JSON)
                    .setDeliveryMode(MessageDeliveryMode.PERSISTENT)
                    .setMessageId(UUID.randomUUID().toString())
                    .build();
            amqpTemplate.convertAndSend(routeKey(), message);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
