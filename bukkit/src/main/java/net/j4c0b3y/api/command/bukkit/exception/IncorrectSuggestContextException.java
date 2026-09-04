package net.j4c0b3y.api.command.bukkit.exception;

/**
 * @author J4C0B3Y
 * @version CommandAPI
 * @since 23/02/2026
 */
public class IncorrectSuggestContextException extends RuntimeException {

    public IncorrectSuggestContextException() {
        super("The suggestion was ran on the incorrect context (thread)!");
    }
}
