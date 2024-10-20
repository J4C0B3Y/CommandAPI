package net.j4c0b3y.api.command.actor;

/**
 * @author J4C0B3Y
 * @version CommandAPI
 * @since 28/08/2024
 */
public abstract class Actor implements PlayerActor, ConsoleActor, ProxyActor {
    public abstract boolean hasPermission(String permission);
    public abstract void sendMessage(String message);
}
