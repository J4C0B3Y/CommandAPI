package net.j4c0b3y.api.command.bukkit.provider.actor;

import net.j4c0b3y.api.command.execution.CommandExecution;
import net.j4c0b3y.api.command.execution.argument.CommandArgument;
import net.j4c0b3y.api.command.wrapper.binding.provider.Provider;
import net.j4c0b3y.api.command.wrapper.binding.provider.ProviderType;
import org.bukkit.command.CommandSender;

/**
 * @author J4C0B3Y
 * @version CommandAPI
 * @since 9/1/24
 */
public class CommandSenderProvider extends Provider<CommandSender> {
    private final BukkitActorProvider bukkitActorProvider;

    public CommandSenderProvider(BukkitActorProvider bukkitActorProvider) {
        super(ProviderType.CONTEXT);
        this.bukkitActorProvider = bukkitActorProvider;
    }

    @Override
    public CommandSender provide(CommandExecution execution, CommandArgument argument) {
        return bukkitActorProvider.provide(execution, argument).getSender();
    }
}
