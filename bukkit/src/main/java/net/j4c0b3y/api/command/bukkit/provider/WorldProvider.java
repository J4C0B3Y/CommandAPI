package net.j4c0b3y.api.command.bukkit.provider;

import net.j4c0b3y.api.command.actor.Actor;
import net.j4c0b3y.api.command.bukkit.BukkitCommandHandler;
import net.j4c0b3y.api.command.exception.execution.ExitMessage;
import net.j4c0b3y.api.command.execution.CommandExecution;
import net.j4c0b3y.api.command.execution.argument.CommandArgument;
import net.j4c0b3y.api.command.wrapper.parameter.provider.Provider;
import net.j4c0b3y.api.command.wrapper.parameter.provider.ProviderType;
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
    public List<String> suggest(Actor actor, CommandArgument argument) {
        List<String> suggestions = new ArrayList<>();

        for (World world : Bukkit.getWorlds()) {
            suggestions.add(world.getName());
        }

        return suggestions;
    }
}
