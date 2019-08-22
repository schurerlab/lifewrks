package edu.miami.ccs.life;

/**
 * @author Sam Abeyruwan
 * @version 0.1
 */
public class LifeException extends Exception {
    public LifeException() {
    }

    public LifeException(String message) {
        super(message);
    }

    public LifeException(String message, Throwable cause) {
        super(message, cause);
    }

    public LifeException(Throwable cause) {
        super(cause);
    }
}
