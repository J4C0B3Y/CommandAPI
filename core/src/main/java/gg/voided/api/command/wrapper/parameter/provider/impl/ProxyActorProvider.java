package gg.voided.api.command.wrapper.parameter.provider.impl;

import gg.voided.api.command.actor.ProxyActor;
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
public class ProxyActorProvider extends Provider<ProxyActor> {

    public ProxyActorProvider() {
        super(ProviderType.CONTEXT);
    }

    @Override
    public ProxyActor provide(CommandExecution execution, CommandArgument argument) throws ExitMessage {
        if (!execution.getActor().isProxy()) {
            throw new ExitMessage("This command can only be run on a proxy.");
        }

        return execution.getActor();
    }
}
