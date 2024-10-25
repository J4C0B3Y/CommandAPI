package net.j4c0b3y.api.command.bukkit.provider.actor;

import net.j4c0b3y.api.command.bukkit.actor.BukkitActor;
import net.j4c0b3y.api.command.exception.execution.ExitMessage;
import net.j4c0b3y.api.command.execution.CommandExecution;
import net.j4c0b3y.api.command.execution.argument.CommandArgument;
import net.j4c0b3y.api.command.wrapper.parameter.provider.Provider;
import net.j4c0b3y.api.command.wrapper.parameter.provider.ProviderType;
import org.bukkit.entity.Player;

/**
 * @author J4C0B3Y
 * @version CommandAPI
 * @since 9/1/24
 */
public class PlayerSenderProvider extends Provider<Player> {
    private final BukkitActorProvider bukkitActorProvider;

    public PlayerSenderProvider(BukkitActorProvider bukkitActorProvider) {
        super(ProviderType.CONTEXT);
        this.bukkitActorProvider = bukkitActorProvider;
    }

    @Override
    public Player provide(CommandExecution execution, CommandArgument argument) {
        BukkitActor bukkitActor = bukkitActorProvider.provide(execution, null);

        if (!bukkitActor.isPlayer()) {
            throw new ExitMessage(execution.getHandler().getLocale().getPlayerOnly());
        }

        return (Player) bukkitActor.getSender();
    }
}
