package gg.voided.api.command.wrapper.parameter.provider.impl;

import gg.voided.api.command.exception.execution.ExitMessage;
import gg.voided.api.command.execution.CommandExecution;
import gg.voided.api.command.execution.argument.CommandArgument;
import gg.voided.api.command.wrapper.CommandWrapper;
import gg.voided.api.command.wrapper.parameter.provider.Provider;
import gg.voided.api.command.wrapper.parameter.provider.ProviderType;

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
