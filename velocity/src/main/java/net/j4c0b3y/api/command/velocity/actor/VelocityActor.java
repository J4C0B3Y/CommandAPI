package net.j4c0b3y.api.command.velocity.actor;

import com.velocitypowered.api.command.CommandSource;
import com.velocitypowered.api.proxy.ConsoleCommandSource;
import com.velocitypowered.api.proxy.Player;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import net.j4c0b3y.api.command.CommandHandler;
import net.j4c0b3y.api.command.actor.Actor;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;

import java.util.UUID;

/**
 * @author J4C0B3Y
 * @version CommandAPI
 * @since 30/08/2024
 */
@Getter
public class VelocityActor extends Actor {
    private final CommandSource source;

    public VelocityActor(CommandSource source, CommandHandler handler) {
        super(handler);
        this.source = source;
    }

    @Override
    public boolean hasPermission(String permission) {
        return permission.isEmpty() || source.hasPermission(permission);
    }

    @Override
    public void sendMessage(String message) {
        source.sendMessage(Component.text(getHandler().getTranslator().apply(message)));
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
