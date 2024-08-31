package gg.voided.api.command.wrapper.parameter.provider.impl;

import gg.voided.api.command.actor.Actor;
import gg.voided.api.command.execution.CommandExecution;
import gg.voided.api.command.execution.argument.CommandArgument;
import gg.voided.api.command.wrapper.parameter.provider.Provider;
import gg.voided.api.command.wrapper.parameter.provider.ProviderType;

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
