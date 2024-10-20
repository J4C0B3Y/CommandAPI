package net.j4c0b3y.api.command.exception.registration;

/**
 * @author J4C0B3Y
 * @version CommandAPI
 * @since 28/08/2024
 */
public class InvalidHandleException extends RuntimeException {
    public InvalidHandleException(String message) {
        super(message);
    }
}
