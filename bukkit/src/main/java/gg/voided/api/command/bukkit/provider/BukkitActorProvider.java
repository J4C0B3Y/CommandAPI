package gg.voided.api.command.bukkit.provider;

import gg.voided.api.command.bukkit.BukkitCommandHandler;
import gg.voided.api.command.bukkit.actor.BukkitActor;
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
public class BukkitActorProvider extends Provider<BukkitActor> {
    private final BukkitCommandHandler handler;

    public BukkitActorProvider(BukkitCommandHandler handler) {
        super(ProviderType.CONTEXT);
        this.handler = handler;
    }

    @Override
    public BukkitActor provide(CommandExecution execution, CommandArgument argument) {
        if (!(execution.getActor() instanceof BukkitActor)) {
            throw new ExitMessage(handler.getBukkitLocale().getBukkitOnly());
        }

        return (BukkitActor) execution.getActor();
    }
}
