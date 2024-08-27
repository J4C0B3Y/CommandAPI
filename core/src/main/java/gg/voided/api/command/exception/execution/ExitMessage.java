package gg.voided.api.command.exception.execution;

/**
 * @author J4C0B3Y
 * @version CommandAPI
 * @since 27/08/2024
 */
public class ExitMessage extends RuntimeException {
    public ExitMessage(String message) {
        super(message);
    }
}
