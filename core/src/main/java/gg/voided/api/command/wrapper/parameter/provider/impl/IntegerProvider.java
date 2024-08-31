package gg.voided.api.command.wrapper.parameter.provider.impl;

import gg.voided.api.command.exception.execution.ExitMessage;
import gg.voided.api.command.execution.CommandExecution;
import gg.voided.api.command.execution.argument.CommandArgument;
import gg.voided.api.command.wrapper.parameter.provider.Provider;
import gg.voided.api.command.wrapper.parameter.provider.ProviderType;

/**
 * @author J4C0B3Y
 * @version CommandAPI
 * @since 27/08/2024
 */
public class IntegerProvider extends Provider<Integer> {

    public IntegerProvider() {
        super(ProviderType.ARGUMENT);
    }

    @Override
    public Integer flagDefault(CommandExecution execution) {
        return 0;
    }



    @Override
    public Integer provide(CommandExecution execution, CommandArgument argument) {
        try {
            return Integer.parseInt(argument.getValue());
        } catch (NumberFormatException exception) {
            throw new ExitMessage("Integer expected, '" + argument.getValue() + "' found.");
        }
    }
}
