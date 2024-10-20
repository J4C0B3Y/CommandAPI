package gg.voided.api.command.wrapper.parameter.modifier.impl;

import gg.voided.api.command.annotation.parameter.modifier.Length;
import gg.voided.api.command.exception.execution.ExitMessage;
import gg.voided.api.command.execution.CommandExecution;
import gg.voided.api.command.wrapper.parameter.CommandParameter;
import gg.voided.api.command.wrapper.parameter.modifier.ArgumentModifier;

/**
 * @author J4C0B3Y
 * @version CommandAPI
 * @since 28/08/2024
 */
public class LengthModifier implements ArgumentModifier<String> {

    @Override
    public String modify(String value, CommandExecution execution, CommandParameter parameter) {
        Length length = parameter.getAnnotation(Length.class);

        if (value.length() < length.min()) {
            throw new ExitMessage(execution.getHandler().getLocale().getBelowMinimum(
                String.valueOf(length.min()), value
            ));
        }

        if (value.length() > length.max()) {
            throw new ExitMessage(execution.getHandler().getLocale().getAboveMaximum(
                String.valueOf(length.max()), value
            ));
        }

        return value;
    }
}
