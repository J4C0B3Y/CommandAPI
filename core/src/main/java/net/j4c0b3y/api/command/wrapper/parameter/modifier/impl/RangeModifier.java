package net.j4c0b3y.api.command.wrapper.parameter.modifier.impl;

import net.j4c0b3y.api.command.annotation.parameter.modifier.Range;
import net.j4c0b3y.api.command.exception.execution.ExitMessage;
import net.j4c0b3y.api.command.execution.CommandExecution;
import net.j4c0b3y.api.command.wrapper.parameter.CommandParameter;
import net.j4c0b3y.api.command.wrapper.parameter.modifier.ArgumentModifier;

/**
 * @author J4C0B3Y
 * @version CommandAPI
 * @since 28/08/2024
 */
public class RangeModifier<T extends Number> implements ArgumentModifier<T> {

    @Override
    public T modify(T value, CommandExecution execution, CommandParameter parameter) {
        Range range = parameter.getAnnotation(Range.class);

        if (value.doubleValue() < range.min()) {
            throw new ExitMessage(execution.getHandler().getLocale().getBelowMinimum(
                String.valueOf(range.min()), value.toString()
            ));
        }

        if (value.doubleValue() > range.max()) {
            throw new ExitMessage(execution.getHandler().getLocale().getAboveMaximum(
                String.valueOf(range.max()), value.toString()
            ));
        }

        return value;
    }
}
