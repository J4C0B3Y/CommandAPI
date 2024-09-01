package gg.voided.api.command.bukkit.provider;

import gg.voided.api.command.exception.execution.ExitMessage;
import gg.voided.api.command.execution.CommandExecution;
import gg.voided.api.command.execution.argument.CommandArgument;
import gg.voided.api.command.wrapper.parameter.provider.Provider;
import gg.voided.api.command.wrapper.parameter.provider.ProviderType;
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
        return bukkitActorProvider.provide(execution, null).getSender();
    }
}
