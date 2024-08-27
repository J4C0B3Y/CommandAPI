package gg.voided.api.command.exception.execution;

/**
 * @author J4C0B3Y
 * @version CommandAPI
 * @since 8/27/24
 */
public class ExitMessage extends RuntimeException {
    public ExitMessage(String message) {
        super(message);
    }
}
