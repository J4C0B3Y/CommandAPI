package net.j4c0b3y.api.command.bungee.provider.actor;

import net.j4c0b3y.api.command.bungee.BungeeCommandHandler;
import net.j4c0b3y.api.command.bungee.actor.BungeeActor;
import net.j4c0b3y.api.command.exception.execution.ExitMessage;
import net.j4c0b3y.api.command.execution.CommandExecution;
import net.j4c0b3y.api.command.execution.argument.CommandArgument;
import net.j4c0b3y.api.command.wrapper.binding.provider.Provider;
import net.j4c0b3y.api.command.wrapper.binding.provider.ProviderType;

/**
 * @author J4C0B3Y
 * @version CommandAPI
 * @since 25/10/2024
 */
public class BungeeActorProvider extends Provider<BungeeActor> {
    private final BungeeCommandHandler handler;

    public BungeeActorProvider(BungeeCommandHandler handler) {
        super(ProviderType.CONTEXT);
        this.handler = handler;
    }

    @Override
    public BungeeActor provide(CommandExecution execution, CommandArgument argument) {
        if (!(execution.getActor() instanceof BungeeActor)) {
            throw new ExitMessage(handler.getBungeeLocale().getBungeeOnly());
        }

        return (BungeeActor) execution.getActor();
    }
}
