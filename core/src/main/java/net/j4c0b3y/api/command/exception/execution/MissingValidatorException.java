package net.j4c0b3y.api.command.exception.execution;

/**
 * @author J4C0B3Y
 * @version CommandAPI
 * @since 25/10/2024
 */
public class MissingValidatorException extends RuntimeException {

    public MissingValidatorException(String message) {
        super(message);
    }
}
