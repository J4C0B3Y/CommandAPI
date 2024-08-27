package gg.voided.api.command.wrapper.parameter.modifier;

import gg.voided.api.command.execution.CommandExecution;
import gg.voided.api.command.wrapper.parameter.CommandParameter;

/**
 * @author J4C0B3Y
 * @version CommandAPI
 * @since 27/08/2024
 */
public interface ArgumentModifier<T> {
    T modify(T value, CommandExecution execution, CommandParameter parameter);
}
