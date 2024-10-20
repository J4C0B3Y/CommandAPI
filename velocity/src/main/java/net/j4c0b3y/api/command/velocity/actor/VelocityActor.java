package net.j4c0b3y.api.command.velocity.actor;

import com.velocitypowered.api.command.CommandSource;
import com.velocitypowered.api.proxy.ConsoleCommandSource;
import com.velocitypowered.api.proxy.Player;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import net.j4c0b3y.api.command.actor.Actor;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;

/**
 * @author J4C0B3Y
 * @version CommandAPI
 * @since 30/08/2024
 */
@Getter
@RequiredArgsConstructor
public class VelocityActor extends Actor {
    private final LegacyComponentSerializer serializer = LegacyComponentSerializer.legacyAmpersand();
    private final CommandSource source;

    @Override
    public boolean hasPermission(String permission) {
        return permission.isEmpty() || source.hasPermission(permission);
    }

    @Override
    public void sendMessage(String message) {
        source.sendMessage(serializer.deserialize(message));
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
    public boolean isProxy() {
        return true;
    }
}
