package gg.voided.api.command.bukkit.provider;

import gg.voided.api.command.actor.Actor;
import gg.voided.api.command.bukkit.BukkitCommandHandler;
import gg.voided.api.command.exception.execution.ExitMessage;
import gg.voided.api.command.execution.CommandExecution;
import gg.voided.api.command.execution.argument.CommandArgument;
import gg.voided.api.command.wrapper.parameter.provider.Provider;
import gg.voided.api.command.wrapper.parameter.provider.ProviderType;
import org.bukkit.Bukkit;
import org.bukkit.World;

import java.util.ArrayList;
import java.util.List;

/**
 * @author J4C0B3Y
 * @version CommandAPI
 * @since 20/10/2024
 */
public class WorldProvider extends Provider<World> {
    private final BukkitCommandHandler handler;

    public WorldProvider(BukkitCommandHandler handler) {
        super(ProviderType.ARGUMENT);
        this.handler = handler;
    }

    @Override
    public World flagDefault(CommandExecution execution) {
        return Bukkit.getWorlds().get(0);
    }

    @Override
    public World provide(CommandExecution execution, CommandArgument argument) {
        World world = Bukkit.getWorld(argument.getValue());

        if (world == null) {
            throw new ExitMessage(handler.getBukkitLocale().getInvalidWorld(argument.getValue()));
        }

        return world;
    }

    @Override
    public List<String> suggest(Actor actor) {
        List<String> suggestions = new ArrayList<>();

        for (World world : Bukkit.getWorlds()) {
            suggestions.add(world.getName());
        }

        return suggestions;
    }
}
