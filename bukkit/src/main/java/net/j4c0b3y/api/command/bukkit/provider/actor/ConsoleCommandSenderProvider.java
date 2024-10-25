package net.j4c0b3y.api.command.bukkit.provider.actor;

import net.j4c0b3y.api.command.bukkit.actor.BukkitActor;
import net.j4c0b3y.api.command.exception.execution.ExitMessage;
import net.j4c0b3y.api.command.execution.CommandExecution;
import net.j4c0b3y.api.command.execution.argument.CommandArgument;
import net.j4c0b3y.api.command.wrapper.binding.provider.Provider;
import net.j4c0b3y.api.command.wrapper.binding.provider.ProviderType;
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
