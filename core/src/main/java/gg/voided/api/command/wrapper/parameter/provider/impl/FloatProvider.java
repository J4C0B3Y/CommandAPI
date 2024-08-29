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
public class FloatProvider extends Provider<Float> {

    public FloatProvider() {
        super(ProviderType.ARGUMENT);
    }

    @Override
    public Float flagDefault(CommandExecution execution) {
        return 0f;
    }

    @Override
    public Float provide(CommandExecution execution, CommandArgument argument) {
        try {
            return Float.parseFloat(argument.getValue());
        } catch (NumberFormatException exception) {
            throw new ExitMessage("Float expected, '" + argument.getValue() + "' found.");
        }
    }
}
