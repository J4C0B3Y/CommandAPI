package gg.voided.api.command.wrapper.parameter.provider.impl;

import gg.voided.api.command.execution.CommandExecution;
import gg.voided.api.command.execution.argument.CommandArgument;
import gg.voided.api.command.wrapper.parameter.provider.ContextProvider;
import lombok.RequiredArgsConstructor;

/**
 * @author J4C0B3Y
 * @version CommandAPI
 * @since 27/08/2024
 */
@RequiredArgsConstructor
public class InstanceProvider<T> extends ContextProvider<T> {
    private final T instance;

    @Override
    public T provide(CommandExecution execution, CommandArgument argument) {
        return instance;
    }
}
