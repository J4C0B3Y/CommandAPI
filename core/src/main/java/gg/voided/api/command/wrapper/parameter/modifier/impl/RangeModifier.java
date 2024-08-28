package gg.voided.api.command.wrapper.parameter.modifier.impl;

import gg.voided.api.command.annotation.parameter.modifier.Range;
import gg.voided.api.command.exception.execution.ExitMessage;
import gg.voided.api.command.execution.CommandExecution;
import gg.voided.api.command.wrapper.parameter.CommandParameter;
import gg.voided.api.command.wrapper.parameter.modifier.ArgumentModifier;

/**
 * @author J4C0B3Y
 * @version CommandAPI
 * @since 28/08/2024
 */
public class RangeModifier<T extends Number> implements ArgumentModifier<T> {

    @Override
    public T modify(T value, CommandExecution execution, CommandParameter parameter) {
        Range range = parameter.getType().getAnnotation(Range.class);

        if (value.doubleValue() < range.min()) {
            throw new ExitMessage("Minimum '" + range.min() + "', found '" + value.doubleValue() + "'.");
        }

        if (value.doubleValue() > range.max()) {
            throw new ExitMessage("Maximum '" + range.max() + "', found '" + value.doubleValue() + "'.");
        }

        return value;
    }
}