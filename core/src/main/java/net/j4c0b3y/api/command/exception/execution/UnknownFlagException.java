package net.j4c0b3y.api.command.exception.execution;

import lombok.Getter;

/**
 * @author J4C0B3Y
 * @version CommandAPI
 * @since 7/09/2024
 */
@Getter
public class UnknownFlagException extends ExitMessage {
    public UnknownFlagException(String message, boolean showUsage) {
        super(message, showUsage);
    }

    public UnknownFlagException(String message) {
        this(message, false);
    }
}
