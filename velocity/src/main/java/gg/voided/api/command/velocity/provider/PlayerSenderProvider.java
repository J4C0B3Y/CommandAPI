package gg.voided.api.command.velocity.provider;

import com.velocitypowered.api.proxy.Player;
import gg.voided.api.command.exception.execution.ExitMessage;
import gg.voided.api.command.execution.CommandExecution;
import gg.voided.api.command.execution.argument.CommandArgument;
import gg.voided.api.command.velocity.actor.VelocityActor;
import gg.voided.api.command.wrapper.parameter.provider.Provider;
import gg.voided.api.command.wrapper.parameter.provider.ProviderType;

/**
 * @author J4C0B3Y
 * @version CommandAPI
 * @since 20/10/2024
 */
public class PlayerSenderProvider extends Provider<Player> {
    private final VelocityActorProvider velocityActorProvider;

    public PlayerSenderProvider(VelocityActorProvider velocityActorProvider) {
        super(ProviderType.CONTEXT);
        this.velocityActorProvider = velocityActorProvider;
    }

    @Override
    public Player provide(CommandExecution execution, CommandArgument argument) {
        VelocityActor velocityActor = velocityActorProvider.provide(execution, null);

        if (!velocityActor.isPlayer()) {
            throw new ExitMessage(execution.getHandler().getLocale().getPlayerOnly());
        }

        return (Player) velocityActor.getSource();
    }
}
