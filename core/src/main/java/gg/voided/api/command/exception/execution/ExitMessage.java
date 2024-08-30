package gg.voided.api.command.exception.execution;

import lombok.Getter;

/**
 * @author J4C0B3Y
 * @version CommandAPI
 * @since 27/08/2024
 */
@Getter
public class ExitMessage extends RuntimeException {
    private final boolean showUsage;

    public ExitMessage(String message, boolean showUsage) {
        super(message);
        this.showUsage = showUsage;
    }

    public ExitMessage(String message) {
        this(message, false);
    }
}
