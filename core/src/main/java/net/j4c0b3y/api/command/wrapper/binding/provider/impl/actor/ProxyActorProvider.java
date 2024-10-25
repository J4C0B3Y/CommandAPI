package net.j4c0b3y.api.command.wrapper.binding.provider.impl.actor;

import net.j4c0b3y.api.command.actor.ProxyActor;
import net.j4c0b3y.api.command.exception.execution.ExitMessage;
import net.j4c0b3y.api.command.execution.CommandExecution;
import net.j4c0b3y.api.command.execution.argument.CommandArgument;
import net.j4c0b3y.api.command.wrapper.binding.provider.ProviderType;
import net.j4c0b3y.api.command.wrapper.binding.provider.Provider;

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
