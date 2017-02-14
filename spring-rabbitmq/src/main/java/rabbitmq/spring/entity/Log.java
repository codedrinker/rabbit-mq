package rabbitmq.spring.entity;

/**
 * Created by codedrinker on 14/02/2017.
 */
public class Log {
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    private String message;
}
