package net.j4c0b3y.api.command.exception.registration;

/**
 * @author J4C0B3Y
 * @version CommandAPI
 * @since 3/11/2024
 */
public class InvalidParameterException extends RuntimeException {
    public InvalidParameterException(String message) {
        super(message);
    }
}
