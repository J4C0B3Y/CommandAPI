package gg.voided.api.command.wrapper.parameter.provider.impl.actor;

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
    public ProxyActor provide(CommandExecution execution, CommandArgument argument) {
        if (!execution.getActor().isProxy()) {
            throw new ExitMessage(execution.getHandler().getLocale().getProxyOnly());
        }

        return execution.getActor();
    }
}
