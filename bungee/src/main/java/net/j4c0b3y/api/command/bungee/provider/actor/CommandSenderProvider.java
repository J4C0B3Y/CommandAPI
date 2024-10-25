package net.j4c0b3y.api.command.bungee.provider.actor;

import net.j4c0b3y.api.command.execution.CommandExecution;
import net.j4c0b3y.api.command.execution.argument.CommandArgument;
import net.j4c0b3y.api.command.wrapper.parameter.provider.Provider;
import net.j4c0b3y.api.command.wrapper.parameter.provider.ProviderType;
import net.md_5.bungee.api.CommandSender;

/**
 * @author J4C0B3Y
 * @version CommandAPI
 * @since 25/10/2024
 */
public class CommandSenderProvider extends Provider<CommandSender> {
    private final BungeeActorProvider bungeeActorProvider;

    public CommandSenderProvider(BungeeActorProvider bungeeActorProvider) {
        super(ProviderType.CONTEXT);
        this.bungeeActorProvider = bungeeActorProvider;
    }

    @Override
    public CommandSender provide(CommandExecution execution, CommandArgument argument) {
        return bungeeActorProvider.provide(execution, argument).getSender();
    }
}
