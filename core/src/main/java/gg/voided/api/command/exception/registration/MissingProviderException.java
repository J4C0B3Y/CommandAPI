package gg.voided.api.command.exception.registration;

/**
 * @author J4C0B3Y
 * @version CommandAPI
 * @since 27/08/2024
 */
public class MissingProviderException extends RuntimeException {
    public MissingProviderException(String message) {
        super(message);
    }
}
