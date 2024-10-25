package net.j4c0b3y.api.command.wrapper.binding.modifier.impl;

import net.j4c0b3y.api.command.annotation.parameter.modifier.Length;
import net.j4c0b3y.api.command.exception.execution.ExitMessage;
import net.j4c0b3y.api.command.execution.CommandExecution;
import net.j4c0b3y.api.command.wrapper.CommandParameter;
import net.j4c0b3y.api.command.wrapper.binding.modifier.ArgumentModifier;

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
