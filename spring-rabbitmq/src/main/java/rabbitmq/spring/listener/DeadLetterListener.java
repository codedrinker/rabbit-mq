package rabbitmq.spring.listener;

import com.rabbitmq.client.Channel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.core.ChannelAwareMessageListener;

/**
 * Created by codedrinker on 13/02/2017.
 */
public class DeadLetterListener implements ChannelAwareMessageListener {
    final Logger logger = LoggerFactory.getLogger(DeadLetterListener.class);

    public void onMessage(Message message, Channel channel) throws Exception {
        logger.info("Receive dead letter, messageId -> {} , message body -> {}", message.getMessageProperties().getMessageId(), new String(message.getBody()));
        channel.basicAck(message.getMessageProperties().getDeliveryTag(), true);
    }
}
