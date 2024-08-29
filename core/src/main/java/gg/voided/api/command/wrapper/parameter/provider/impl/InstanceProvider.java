package gg.voided.api.command.wrapper.parameter.provider.impl;

import gg.voided.api.command.execution.CommandExecution;
import gg.voided.api.command.execution.argument.CommandArgument;
import gg.voided.api.command.wrapper.parameter.provider.Provider;
import gg.voided.api.command.wrapper.parameter.provider.ProviderType;

/**
 * @author J4C0B3Y
 * @version CommandAPI
 * @since 27/08/2024
 */
public class InstanceProvider<T> extends Provider<T> {
    private final T instance;

    public InstanceProvider(T instance) {
        super(ProviderType.CONTEXT);
        this.instance = instance;
    }

    @Override
    public T provide(CommandExecution execution, CommandArgument argument) {
        return instance;
    }
}
