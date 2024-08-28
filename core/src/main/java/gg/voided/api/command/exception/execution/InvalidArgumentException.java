package gg.voided.api.command.exception.execution;

/**
 * @author J4C0B3Y
 * @version CommandAPI
 * @since 29/08/2024
 */
public class InvalidArgumentException extends RuntimeException {
    public InvalidArgumentException(String message) {
        super(message);
    }
}
