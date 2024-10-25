package net.j4c0b3y.api.command.wrapper.binding.provider.impl.actor;

import net.j4c0b3y.api.command.actor.PlayerActor;
import net.j4c0b3y.api.command.exception.execution.ExitMessage;
import net.j4c0b3y.api.command.execution.CommandExecution;
import net.j4c0b3y.api.command.execution.argument.CommandArgument;
import net.j4c0b3y.api.command.wrapper.binding.provider.ProviderType;
import net.j4c0b3y.api.command.wrapper.binding.provider.Provider;

/**
 * @author J4C0B3Y
 * @version CommandAPI
 * @since 1/09/2024
 */
public class PlayerActorProvider extends Provider<PlayerActor> {

    public PlayerActorProvider() {
        super(ProviderType.CONTEXT);
    }

    @Override
    public PlayerActor provide(CommandExecution execution, CommandArgument argument) {
        if (!execution.getActor().isPlayer()) {
            throw new ExitMessage(execution.getHandler().getLocale().getPlayerOnly());
        }

        return execution.getActor();
    }
}
