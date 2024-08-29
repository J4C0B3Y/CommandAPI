package gg.voided.api.command.wrapper.parameter.provider.impl;

import gg.voided.api.command.execution.CommandExecution;
import gg.voided.api.command.execution.argument.CommandArgument;
import gg.voided.api.command.wrapper.parameter.provider.Provider;
import gg.voided.api.command.wrapper.parameter.provider.ProviderType;

/**
 * @author J4C0B3Y
 * @version CommandAPI
 * @since 27/08/24
 */
public class StringProvider extends Provider<String> {

    public StringProvider() {
        super(ProviderType.ARGUMENT);
    }

    @Override
    public String provide(CommandExecution execution, CommandArgument argument) {
        return argument.getValue();
    }
}
