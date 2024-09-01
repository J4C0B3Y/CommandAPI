package gg.voided.api.command.wrapper.parameter.provider.impl;

import gg.voided.api.command.actor.PlayerActor;
import gg.voided.api.command.exception.execution.ExitMessage;
import gg.voided.api.command.execution.CommandExecution;
import gg.voided.api.command.execution.argument.CommandArgument;
import gg.voided.api.command.wrapper.parameter.provider.Provider;
import gg.voided.api.command.wrapper.parameter.provider.ProviderType;

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
            throw new ExitMessage("This command can only be run by players.");
        }

        return execution.getActor();
    }
}
