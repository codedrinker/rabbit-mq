package rabbitmq.spring.listener;

import com.alibaba.fastjson.JSON;
import rabbitmq.spring.entity.Log;
import com.rabbitmq.client.Channel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.core.ChannelAwareMessageListener;

/**
 * Created by codedrinker on 14/02/2017.
 */
public abstract class LogListener implements ChannelAwareMessageListener {
    final Logger logger = LoggerFactory.getLogger(DeadLetterListener.class);

    public void onMessage(Message message, Channel channel) throws Exception {
        try {
            logger.info("Receive message, messageId -> {} , message body -> {}", message.getMessageProperties().getMessageId(), new String(message.getBody()));
            handle(JSON.parseObject(new String(message.getBody()), Log.class));
            channel.basicAck(message.getMessageProperties().getDeliveryTag(), true);
        } catch (Exception e) {
            logger.error("Handle message -> {} error", new String(message.getBody()));
            logger.error("Handle message error", e);
            channel.basicReject(message.getMessageProperties().getDeliveryTag(), false);
        }
    }

    protected abstract void handle(Log log);
}
