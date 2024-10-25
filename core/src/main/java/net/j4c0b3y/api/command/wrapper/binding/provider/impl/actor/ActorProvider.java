package net.j4c0b3y.api.command.wrapper.binding.provider.impl.actor;

import net.j4c0b3y.api.command.actor.Actor;
import net.j4c0b3y.api.command.execution.CommandExecution;
import net.j4c0b3y.api.command.execution.argument.CommandArgument;
import net.j4c0b3y.api.command.wrapper.binding.provider.Provider;
import net.j4c0b3y.api.command.wrapper.binding.provider.ProviderType;

/**
 * @author J4C0B3Y
 * @version CommandAPI
 * @since 31/08/2024
 */
public class ActorProvider extends Provider<Actor> {

    public ActorProvider() {
        super(ProviderType.CONTEXT);
    }

    @Override
    public Actor provide(CommandExecution execution, CommandArgument argument) {
        return execution.getActor();
    }
}
