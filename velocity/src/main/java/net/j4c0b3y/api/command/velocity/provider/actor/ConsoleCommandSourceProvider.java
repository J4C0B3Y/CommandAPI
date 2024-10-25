package net.j4c0b3y.api.command.velocity.provider.actor;

import com.velocitypowered.api.proxy.ConsoleCommandSource;
import net.j4c0b3y.api.command.exception.execution.ExitMessage;
import net.j4c0b3y.api.command.execution.CommandExecution;
import net.j4c0b3y.api.command.execution.argument.CommandArgument;
import net.j4c0b3y.api.command.velocity.actor.VelocityActor;
import net.j4c0b3y.api.command.wrapper.binding.provider.Provider;
import net.j4c0b3y.api.command.wrapper.binding.provider.ProviderType;

/**
 * @author J4C0B3Y
 * @version CommandAPI
 * @since 25/10/2024
 */
public class ConsoleCommandSourceProvider extends Provider<ConsoleCommandSource> {
    private final VelocityActorProvider velocityActorProvider;

    public ConsoleCommandSourceProvider(VelocityActorProvider velocityActorProvider) {
        super(ProviderType.CONTEXT);
        this.velocityActorProvider = velocityActorProvider;
    }

    @Override
    public ConsoleCommandSource provide(CommandExecution execution, CommandArgument argument) {
        VelocityActor velocityActor = velocityActorProvider.provide(execution, argument);

        if (!velocityActor.isConsole()) {
            throw new ExitMessage(execution.getHandler().getLocale().getConsoleOnly());
        }

        return (ConsoleCommandSource) velocityActor.getSource();
    }
}
