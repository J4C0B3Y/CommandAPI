package gg.voided.api.command.bukkit;

import gg.voided.api.command.actor.Actor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

/**
 * @author J4C0B3Y
 * @version CommandAPI
 * @since 30/08/2024
 */
@Getter
@RequiredArgsConstructor
public class BukkitActor extends Actor {
    private final CommandSender sender;

    @Override
    public boolean hasPermission(String permission) {
        return sender.hasPermission(permission);
    }

    @Override
    public boolean isConsole() {
        return sender instanceof ConsoleCommandSender;
    }

    @Override
    public boolean isPlayer() {
        return sender instanceof Player;
    }

    @Override
    public boolean isProxy() {
        return false;
    }
}
