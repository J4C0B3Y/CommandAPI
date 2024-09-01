package gg.voided.api.command.wrapper.parameter.provider.impl;

import gg.voided.api.command.actor.ConsoleActor;
import gg.voided.api.command.exception.execution.ExitMessage;
import gg.voided.api.command.execution.CommandExecution;
import gg.voided.api.command.execution.argument.CommandArgument;
import gg.voided.api.command.wrapper.parameter.provider.Provider;
import gg.voided.api.command.wrapper.parameter.provider.ProviderType;

/**
 * @author J4C0B3Y
 * @version CommandAPI
 * @since 9/1/24
 */
public class ConsoleActorProvider extends Provider<ConsoleActor> {

    public ConsoleActorProvider() {
        super(ProviderType.CONTEXT);
    }

    @Override
    public ConsoleActor provide(CommandExecution execution, CommandArgument argument) {

        return null;
    }
}
