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
public class DoubleProvider extends Provider<Double> {

    public DoubleProvider() {
        super(ProviderType.ARGUMENT);
    }

    @Override
    public Double flagDefault(CommandExecution execution) {
        return 0D;
    }

    @Override
    public Double provide(CommandExecution execution, CommandArgument argument) {
        try {
            return Double.parseDouble(argument.getValue());
        } catch (NumberFormatException exception) {
            throw new ExitMessage("Double expected, '" + argument.getValue() + "' found.");
        }
    }
}
