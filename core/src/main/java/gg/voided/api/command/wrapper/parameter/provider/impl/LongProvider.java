package gg.voided.api.command.wrapper.parameter.provider.impl;

import gg.voided.api.command.exception.execution.ExitMessage;
import gg.voided.api.command.execution.CommandExecution;
import gg.voided.api.command.execution.argument.CommandArgument;
import gg.voided.api.command.wrapper.parameter.provider.ArgumentProvider;

/**
 * @author J4C0B3Y
 * @version CommandAPI
 * @since 8/27/24
 */
public class LongProvider extends ArgumentProvider<Long> {

    @Override
    public Long provide(CommandExecution execution, CommandArgument argument) {
        try {
            return Long.parseLong(argument.getValue());
        } catch (NumberFormatException exception) {
            throw new ExitMessage("Long expected, '" + argument.getValue() + "' found.");
        }
    }
}
