package gg.voided.api.command.velocity.provider;

import gg.voided.api.command.exception.execution.ExitMessage;
import gg.voided.api.command.execution.CommandExecution;
import gg.voided.api.command.execution.argument.CommandArgument;
import gg.voided.api.command.velocity.VelocityCommandHandler;
import gg.voided.api.command.velocity.actor.VelocityActor;
import gg.voided.api.command.wrapper.parameter.provider.Provider;
import gg.voided.api.command.wrapper.parameter.provider.ProviderType;

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
