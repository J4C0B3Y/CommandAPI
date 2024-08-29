package gg.voided.api.command.velocity;

import com.velocitypowered.api.command.CommandSource;
import com.velocitypowered.api.proxy.ConsoleCommandSource;
import com.velocitypowered.api.proxy.Player;
import gg.voided.api.command.actor.Actor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * @author J4C0B3Y
 * @version CommandAPI
 * @since 30/08/2024
 */
@Getter
@RequiredArgsConstructor
public class VelocityActor extends Actor {
    private final CommandSource source;

    @Override
    public boolean hasPermission(String permission) {
        return source.hasPermission(permission);
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
