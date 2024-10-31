package net.j4c0b3y.api.command.velocity.actor;

import com.velocitypowered.api.command.CommandSource;
import com.velocitypowered.api.proxy.ConsoleCommandSource;
import com.velocitypowered.api.proxy.Player;
import lombok.Getter;
import net.j4c0b3y.api.command.actor.Actor;
import net.j4c0b3y.api.command.velocity.VelocityCommandHandler;

import java.util.UUID;

/**
 * @author J4C0B3Y
 * @version CommandAPI
 * @since 30/08/2024
 */
@Getter
public class VelocityActor extends Actor {
    private final CommandSource source;
    private final VelocityCommandHandler handler;

    public VelocityActor(CommandSource source, VelocityCommandHandler handler) {
        super(handler);
        this.handler = handler;
        this.source = source;
    }

    @Override
    public boolean hasPermission(String permission) {
        return permission == null || source.hasPermission(permission);
    }

    @Override
    public void sendMessage(String message) {
        String translated = handler.getTranslator().apply(message);
        source.sendMessage(handler.getVelocityTranslator().apply(translated));
    }

    @Override
    public boolean isConsole() {
        return source instanceof ConsoleCommandSource;
    }

    @Override
    public boolean isPlayer() {
        return source instanceof Player;
    }

    @Override
    public UUID getUniqueId() {
        return isPlayer() ? ((Player) source).getUniqueId() : null;
    }

    public Player getPlayer() {
        if (!isPlayer()) {
            throw new IllegalStateException("Source is not a player!");
        }

        return (Player) source;
    }

    @Override
    public boolean isProxy() {
        return true;
    }
}
