package net.j4c0b3y.api.command.wrapper.binding.condition;

import net.j4c0b3y.api.command.execution.CommandExecution;

/**
 * @author J4C0B3Y
 * @version CommandAPI
 * @since 25/10/2024
 */
public interface ExecuteCondition<T> {
    boolean validate(CommandExecution execution, T annotation);
}