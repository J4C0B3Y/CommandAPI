package net.j4c0b3y.api.command.bukkit.provider;

import net.j4c0b3y.api.command.execution.CommandExecution;
import net.j4c0b3y.api.command.execution.argument.CommandArgument;
import net.j4c0b3y.api.command.wrapper.parameter.provider.Provider;
import net.j4c0b3y.api.command.wrapper.parameter.provider.ProviderType;
import org.bukkit.OfflinePlayer;

/**
 * @author J4C0B3Y
 * @version CommandAPI
 * @since 9/1/24
 */
public class OfflinePlayerProvider extends Provider<OfflinePlayer> {
    public OfflinePlayerProvider() {
        super(ProviderType.ARGUMENT);
    }

    @Override
    public OfflinePlayer provide(CommandExecution execution, CommandArgument argument) {
        //TODO: offline player provider
        return null;
    }
}
