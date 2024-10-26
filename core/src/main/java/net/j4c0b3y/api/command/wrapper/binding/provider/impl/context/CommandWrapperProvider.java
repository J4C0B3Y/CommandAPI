package net.j4c0b3y.api.command.wrapper.binding.provider.impl.context;

import net.j4c0b3y.api.command.execution.CommandExecution;
import net.j4c0b3y.api.command.execution.argument.CommandArgument;
import net.j4c0b3y.api.command.wrapper.CommandWrapper;
import net.j4c0b3y.api.command.wrapper.binding.provider.Provider;
import net.j4c0b3y.api.command.wrapper.binding.provider.ProviderType;

/**
 * @author J4C0B3Y
 * @version CommandAPI
 * @since 1/09/2024
 */
public class CommandWrapperProvider extends Provider<CommandWrapper> {

    public CommandWrapperProvider() {
        super(ProviderType.CONTEXT);
    }

    @Override
    public CommandWrapper provide(CommandExecution execution, CommandArgument argument) {
        return execution.getHandle().getWrapper();
    }
}
