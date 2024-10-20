package net.j4c0b3y.api.command.velocity.provider;

import com.velocitypowered.api.command.CommandSource;
import net.j4c0b3y.api.command.execution.CommandExecution;
import net.j4c0b3y.api.command.execution.argument.CommandArgument;
import net.j4c0b3y.api.command.wrapper.parameter.provider.Provider;
import net.j4c0b3y.api.command.wrapper.parameter.provider.ProviderType;

/**
 * @author J4C0B3Y
 * @version CommandAPI
 * @since 20/10/2024
 */
public class CommandSourceProvider extends Provider<CommandSource> {
    private final VelocityActorProvider velocityActorProvider;

    public CommandSourceProvider(VelocityActorProvider velocityActorProvider) {
        super(ProviderType.CONTEXT);
        this.velocityActorProvider = velocityActorProvider;
    }

    @Override
    public CommandSource provide(CommandExecution execution, CommandArgument argument) {
        return velocityActorProvider.provide(execution, null).getSource();
    }
}
