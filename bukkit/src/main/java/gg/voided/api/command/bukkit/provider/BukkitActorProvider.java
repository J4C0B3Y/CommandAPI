package gg.voided.api.command.bukkit.provider;

import gg.voided.api.command.bukkit.BukkitActor;
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

    public BukkitActorProvider() {
        super(ProviderType.CONTEXT);
    }

    @Override
    public BukkitActor provide(CommandExecution execution, CommandArgument argument) throws ExitMessage {
        if (!(execution.getActor() instanceof BukkitActor)) {
            throw new ExitMessage("This command can only be run on a bukkit server");
        }

        return (BukkitActor) execution.getActor();
    }
}
