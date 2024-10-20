package gg.voided.api.command.bukkit.provider;

import gg.voided.api.command.bukkit.actor.BukkitActor;
import gg.voided.api.command.exception.execution.ExitMessage;
import gg.voided.api.command.execution.CommandExecution;
import gg.voided.api.command.execution.argument.CommandArgument;
import gg.voided.api.command.wrapper.parameter.provider.Provider;
import gg.voided.api.command.wrapper.parameter.provider.ProviderType;
import org.bukkit.command.ConsoleCommandSender;

/**
 * @author J4C0B3Y
 * @version CommandAPI
 * @since 9/1/24
 */
public class ConsoleCommandSenderProvider extends Provider<ConsoleCommandSender> {
    private final BukkitActorProvider bukkitActorProvider;

    public ConsoleCommandSenderProvider(BukkitActorProvider bukkitActorProvider) {
        super(ProviderType.CONTEXT);
        this.bukkitActorProvider = bukkitActorProvider;
    }

    @Override
    public ConsoleCommandSender provide(CommandExecution execution, CommandArgument argument) {
        BukkitActor bukkitActor = bukkitActorProvider.provide(execution, null);

        if (!bukkitActor.isConsole()) {
            throw new ExitMessage(execution.getHandler().getLocale().getConsoleOnly());
        }

        return (ConsoleCommandSender) bukkitActor.getSender();
    }
}
