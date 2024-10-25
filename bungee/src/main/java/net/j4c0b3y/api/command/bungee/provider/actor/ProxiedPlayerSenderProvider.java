package net.j4c0b3y.api.command.bungee.provider.actor;

import net.j4c0b3y.api.command.bungee.actor.BungeeActor;
import net.j4c0b3y.api.command.exception.execution.ExitMessage;
import net.j4c0b3y.api.command.execution.CommandExecution;
import net.j4c0b3y.api.command.execution.argument.CommandArgument;
import net.j4c0b3y.api.command.wrapper.binding.provider.Provider;
import net.j4c0b3y.api.command.wrapper.binding.provider.ProviderType;
import net.md_5.bungee.api.connection.ProxiedPlayer;

/**
 * @author J4C0B3Y
 * @version CommandAPI
 * @since 25/10/2024
 */
public class ProxiedPlayerSenderProvider extends Provider<ProxiedPlayer> {
    private final BungeeActorProvider bungeeActorProvider;

    public ProxiedPlayerSenderProvider(BungeeActorProvider bungeeActorProvider) {
        super(ProviderType.CONTEXT);
        this.bungeeActorProvider = bungeeActorProvider;
    }

    @Override
    public ProxiedPlayer provide(CommandExecution execution, CommandArgument argument) {
        BungeeActor bungeeActor = bungeeActorProvider.provide(execution, null);

        if (!bungeeActor.isPlayer()) {
            throw new ExitMessage(execution.getHandler().getLocale().getPlayerOnly());
        }

        return (ProxiedPlayer) bungeeActor.getSender();
    }
}
