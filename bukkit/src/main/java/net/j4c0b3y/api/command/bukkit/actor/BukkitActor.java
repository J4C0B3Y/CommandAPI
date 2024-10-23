package net.j4c0b3y.api.command.bukkit.actor;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import net.j4c0b3y.api.command.CommandHandler;
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
public class BukkitActor extends Actor {
    private final CommandSender sender;

    public BukkitActor(CommandSender sender, CommandHandler handler) {
        super(handler);
        this.sender = sender;
    }

    @Override
    public boolean hasPermission(String permission) {
        return permission.isEmpty() || sender.hasPermission(permission);
    }

    @Override
    public void sendMessage(String message) {
        sender.sendMessage(getHandler().getTranslator().apply(message));
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

    public Player getPlayer() {
        if (!isPlayer()) {
            throw new IllegalStateException("Sender is not a player!");
        }

        return (Player) sender;
    }

    @Override
    public boolean isProxy() {
        return false;
    }
}
