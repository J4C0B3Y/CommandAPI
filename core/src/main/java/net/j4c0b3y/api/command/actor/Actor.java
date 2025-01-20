package net.j4c0b3y.api.command.actor;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import net.j4c0b3y.api.command.CommandHandler;

/**
 * @author J4C0B3Y
 * @version CommandAPI
 * @since 28/08/2024
 */
@Getter
@RequiredArgsConstructor
public abstract class Actor implements PlayerActor, ConsoleActor, ProxyActor {
    private final CommandHandler handler;

    public abstract boolean hasPermission(String permission);
    public abstract void sendMessage(String message);

    public abstract String getName();
}
