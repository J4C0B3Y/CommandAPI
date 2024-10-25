package net.j4c0b3y.api.command.bukkit.provider.argument;

import net.j4c0b3y.api.command.actor.Actor;
import net.j4c0b3y.api.command.bukkit.BukkitCommandHandler;
import net.j4c0b3y.api.command.bukkit.actor.BukkitActor;
import net.j4c0b3y.api.command.exception.execution.ExitMessage;
import net.j4c0b3y.api.command.execution.CommandExecution;
import net.j4c0b3y.api.command.execution.argument.CommandArgument;
import net.j4c0b3y.api.command.wrapper.binding.provider.Provider;
import net.j4c0b3y.api.command.wrapper.binding.provider.ProviderType;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

/**
 * @author J4C0B3Y
 * @version CommandAPI
 * @since 1/09/24
 */
public class PlayerProvider extends Provider<Player> {
    private final BukkitCommandHandler handler;

    public PlayerProvider(BukkitCommandHandler handler) {
        super(ProviderType.ARGUMENT, "player");
        this.handler = handler;
    }

    @Override
    public Player provide(CommandExecution execution, CommandArgument argument) {
        Player player = Bukkit.getPlayer(argument.getValue());

        if (player == null || !canSee(execution.getActor(), player)) {
            throw new ExitMessage(handler.getBukkitLocale().getPlayerOffline(argument.getValue()));
        }

        return player;
    }

    @Override
    public List<String> suggest(Actor actor, CommandArgument argument) {
        List<String> suggestions = new ArrayList<>();

        for (Player player : Bukkit.getOnlinePlayers()) {
            if (canSee(actor, player)) {
                suggestions.add(player.getName());
            }
        }

        return suggestions;
    }

    private boolean canSee(Actor actor, Player target) {
        if (!(actor instanceof BukkitActor)) return true;
        BukkitActor bukkitActor = (BukkitActor) actor;

        if (!bukkitActor.isPlayer()) return true;
        Player player = (Player) bukkitActor.getSender();

        return player.canSee(target);
    }
}
