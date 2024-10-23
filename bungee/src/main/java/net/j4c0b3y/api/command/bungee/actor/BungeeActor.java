package net.j4c0b3y.api.command.bungee.actor;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import net.j4c0b3y.api.command.actor.Actor;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;

import java.util.UUID;

/**
 * @author J4C0B3Y
 * @version CommandAPI
 * @since 30/08/2024
 */
@Getter
@RequiredArgsConstructor
public class BungeeActor extends Actor {
    private final CommandSender sender;

    @Override
    public boolean hasPermission(String permission) {
        return permission.isEmpty() || sender.hasPermission(permission);
    }

    @Override
    public void sendMessage(String message) {
        sender.sendMessage(TextComponent.fromLegacy(message));
    }

    @Override
    public boolean isConsole() {
        return sender.equals(ProxyServer.getInstance().getConsole());
    }

    @Override
    public boolean isPlayer() {
        return sender instanceof ProxiedPlayer;
    }

    @Override
    public UUID getUniqueId() {
        return isPlayer() ? ((ProxiedPlayer) sender).getUniqueId() : null;
    }

    @Override
    public boolean isProxy() {
        return true;
    }
}
