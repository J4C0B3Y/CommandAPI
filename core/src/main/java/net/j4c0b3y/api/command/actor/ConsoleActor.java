package net.j4c0b3y.api.command.actor;

/**
 * @author J4C0B3Y
 * @version CommandAPI
 * @since 28/08/2024
 */
public interface ConsoleActor {
    boolean isConsole();
    void sendMessage(String message);
}
