package gg.voided.api.command.bukkit.provider;

import gg.voided.api.command.actor.Actor;
import gg.voided.api.command.bukkit.BukkitActor;
import gg.voided.api.command.exception.execution.ExitMessage;
import gg.voided.api.command.execution.CommandExecution;
import gg.voided.api.command.execution.argument.CommandArgument;
import gg.voided.api.command.wrapper.parameter.provider.Provider;
import gg.voided.api.command.wrapper.parameter.provider.ProviderType;
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

    public PlayerProvider() {
        super(ProviderType.ARGUMENT);
    }

    @Override
    public Player provide(CommandExecution execution, CommandArgument argument) {
        Player player = Bukkit.getPlayer(argument.getValue());

        if (player == null || canSee(execution.getActor(), player)) {
            throw new ExitMessage("A player with that name is not online.");
        }

        return player;
    }

    @Override
    public List<String> suggest(Actor actor, String prefix) {
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
