package net.j4c0b3y.api.command.velocity.provider;

import com.velocitypowered.api.proxy.Player;
import net.j4c0b3y.api.command.exception.execution.ExitMessage;
import net.j4c0b3y.api.command.execution.CommandExecution;
import net.j4c0b3y.api.command.execution.argument.CommandArgument;
import net.j4c0b3y.api.command.velocity.actor.VelocityActor;
import net.j4c0b3y.api.command.wrapper.parameter.provider.Provider;
import net.j4c0b3y.api.command.wrapper.parameter.provider.ProviderType;

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
