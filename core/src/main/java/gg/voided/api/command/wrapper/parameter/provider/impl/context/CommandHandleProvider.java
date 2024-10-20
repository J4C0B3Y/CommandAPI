package gg.voided.api.command.wrapper.parameter.provider.impl.context;

import gg.voided.api.command.execution.CommandExecution;
import gg.voided.api.command.execution.argument.CommandArgument;
import gg.voided.api.command.wrapper.CommandHandle;
import gg.voided.api.command.wrapper.parameter.provider.Provider;
import gg.voided.api.command.wrapper.parameter.provider.ProviderType;

/**
 * @author J4C0B3Y
 * @version CommandAPI
 * @since 1/09/2024
 */
public class CommandHandleProvider extends Provider<CommandHandle> {

    public CommandHandleProvider() {
        super(ProviderType.CONTEXT);
    }

    @Override
    public CommandHandle provide(CommandExecution execution, CommandArgument argument) {
        return execution.getHandle();
    }
}
