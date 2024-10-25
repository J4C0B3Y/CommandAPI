package net.j4c0b3y.api.command.velocity.provider.actor;

import net.j4c0b3y.api.command.exception.execution.ExitMessage;
import net.j4c0b3y.api.command.execution.CommandExecution;
import net.j4c0b3y.api.command.execution.argument.CommandArgument;
import net.j4c0b3y.api.command.velocity.VelocityCommandHandler;
import net.j4c0b3y.api.command.velocity.actor.VelocityActor;
import net.j4c0b3y.api.command.wrapper.parameter.provider.Provider;
import net.j4c0b3y.api.command.wrapper.parameter.provider.ProviderType;

/**
 * @author J4C0B3Y
 * @version CommandAPI
 * @since 20/10/2024
 */
public class VelocityActorProvider extends Provider<VelocityActor> {
    private final VelocityCommandHandler handler;

    public VelocityActorProvider(VelocityCommandHandler handler) {
        super(ProviderType.CONTEXT);
        this.handler = handler;
    }

    @Override
    public VelocityActor provide(CommandExecution execution, CommandArgument argument) {
        if (!(execution.getActor() instanceof VelocityActor)) {
            throw new ExitMessage(handler.getVelocityLocale().getVelocityOnly());
        }

        return (VelocityActor) execution.getActor();
    }
}
