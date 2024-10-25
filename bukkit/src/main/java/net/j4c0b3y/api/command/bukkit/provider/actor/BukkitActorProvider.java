package net.j4c0b3y.api.command.bukkit.provider.actor;

import net.j4c0b3y.api.command.bukkit.BukkitCommandHandler;
import net.j4c0b3y.api.command.bukkit.actor.BukkitActor;
import net.j4c0b3y.api.command.exception.execution.ExitMessage;
import net.j4c0b3y.api.command.execution.CommandExecution;
import net.j4c0b3y.api.command.execution.argument.CommandArgument;
import net.j4c0b3y.api.command.wrapper.binding.provider.Provider;
import net.j4c0b3y.api.command.wrapper.binding.provider.ProviderType;

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
