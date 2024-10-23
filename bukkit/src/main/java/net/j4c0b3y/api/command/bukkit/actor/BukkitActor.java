package net.j4c0b3y.api.command.bukkit.actor;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import net.j4c0b3y.api.command.actor.Actor;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

import java.util.UUID;

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
        return permission.isEmpty() || sender.hasPermission(permission);
    }

    @Override
    public void sendMessage(String message) {
        sender.sendMessage(ChatColor.translateAlternateColorCodes('&', message));
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
    public UUID getUniqueId() {
        return isPlayer() ? ((Player) sender).getUniqueId() : null;
    }

    @Override
    public boolean isProxy() {
        return false;
    }
}
