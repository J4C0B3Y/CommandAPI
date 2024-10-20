package net.j4c0b3y.api.command.wrapper.parameter.modifier;

import net.j4c0b3y.api.command.execution.CommandExecution;
import net.j4c0b3y.api.command.wrapper.parameter.CommandParameter;

/**
 * @author J4C0B3Y
 * @version CommandAPI
 * @since 27/08/2024
 */
public interface ArgumentModifier<T> {
    T modify(T value, CommandExecution execution, CommandParameter parameter);
}
