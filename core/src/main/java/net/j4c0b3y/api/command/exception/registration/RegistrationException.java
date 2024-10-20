package net.j4c0b3y.api.command.exception.registration;

/**
 * @author J4C0B3Y
 * @version CommandAPI
 * @since 30/08/2024
 */
public class RegistrationException extends RuntimeException {
    public RegistrationException(String message) {
        super(message);
    }

    public RegistrationException(String message, Throwable throwable) {
        super(message, throwable);
    }
}
