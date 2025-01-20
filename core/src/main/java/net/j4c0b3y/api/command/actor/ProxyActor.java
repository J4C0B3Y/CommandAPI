package net.j4c0b3y.api.command.actor;

/**
 * @author J4C0B3Y
 * @version CommandAPI
 * @since 28/08/2024
 */
public interface ProxyActor {
    boolean isProxy();
    boolean hasPermission(String permission);
    void sendMessage(String message);
    String getName();
}
