package net.j4c0b3y.api.command.wrapper.parameter.provider.impl.context;

import net.j4c0b3y.api.command.execution.CommandExecution;
import net.j4c0b3y.api.command.execution.argument.CommandArgument;
import net.j4c0b3y.api.command.wrapper.parameter.provider.Provider;
import net.j4c0b3y.api.command.wrapper.parameter.provider.ProviderType;

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
