package net.j4c0b3y.api.command.bukkit.provider.argument;

import net.j4c0b3y.api.command.actor.Actor;
import net.j4c0b3y.api.command.bukkit.BukkitCommandHandler;
import net.j4c0b3y.api.command.exception.execution.ExitMessage;
import net.j4c0b3y.api.command.execution.CommandExecution;
import net.j4c0b3y.api.command.execution.argument.CommandArgument;
import net.j4c0b3y.api.command.wrapper.binding.provider.Provider;
import net.j4c0b3y.api.command.wrapper.binding.provider.ProviderType;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;

import java.util.ArrayList;
import java.util.List;

/**
 * @author J4C0B3Y
 * @version CommandAPI
 * @since 9/1/24
 */
public class OfflinePlayerProvider extends Provider<OfflinePlayer> {
    private final BukkitCommandHandler handler;

    public OfflinePlayerProvider(BukkitCommandHandler bukkitCommandHandler) {
        super(ProviderType.ARGUMENT, "player");
        this.handler = bukkitCommandHandler;
    }

    @Override
    @SuppressWarnings("deprecation")
    public OfflinePlayer provide(CommandExecution execution, CommandArgument argument) {
        OfflinePlayer player = Bukkit.getOfflinePlayer(argument.getValue());

        if (!player.hasPlayedBefore() && !player.isOnline() && player.getFirstPlayed() == 0) {
            throw new ExitMessage(handler.getBukkitLocale()
                .getInvalidOfflinePlayer(argument.getValue())
            );
        }

        return player;
    }

    @Override
    public List<String> suggest(Actor actor, CommandArgument argument) {
        List<String> suggestions = new ArrayList<>();

        for (OfflinePlayer player : Bukkit.getOfflinePlayers()) {
            suggestions.add(player.getName());
        }

        return suggestions;
    }
}
