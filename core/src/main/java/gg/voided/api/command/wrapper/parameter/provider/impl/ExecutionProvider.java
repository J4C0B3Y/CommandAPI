package gg.voided.api.command.wrapper.parameter.provider.impl;

import gg.voided.api.command.execution.CommandExecution;
import gg.voided.api.command.execution.argument.CommandArgument;
import gg.voided.api.command.wrapper.parameter.provider.Provider;
import gg.voided.api.command.wrapper.parameter.provider.ProviderType;

/**
 * @author J4C0B3Y
 * @version CommandAPI
 * @since 27/08/2024
 */
public class ExecutionProvider extends Provider<CommandExecution> {

    public ExecutionProvider() {
        super(ProviderType.CONTEXT);
    }

    @Override
    public CommandExecution provide(CommandExecution execution, CommandArgument argument) {
        return execution;
    }
}
