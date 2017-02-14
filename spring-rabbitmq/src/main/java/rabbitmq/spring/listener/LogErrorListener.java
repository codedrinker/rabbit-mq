package rabbitmq.spring.listener;

import rabbitmq.spring.entity.Log;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by codedrinker on 13/02/2017.
 */
public class LogErrorListener extends LogListener {
    final Logger logger = LoggerFactory.getLogger(LogErrorListener.class);

    protected void handle(Log log) {
        logger.info("Handle error log -> {}");
    }
}
