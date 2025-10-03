package net.j4c0b3y.api.command.wrapper.binding.provider.impl.argument;

import net.j4c0b3y.api.command.execution.CommandExecution;
import net.j4c0b3y.api.command.execution.argument.CommandArgument;
import net.j4c0b3y.api.command.wrapper.binding.provider.Provider;
import net.j4c0b3y.api.command.wrapper.binding.provider.ProviderType;
import net.j4c0b3y.api.command.wrapper.suggestion.CommandSuggestion;

import java.lang.reflect.Array;
import java.util.List;

/**
 * @author J4C0B3Y
 * @version CommandAPI
 * @since 4/10/2025
 */
public class ArrayProvider<T> extends Provider<T[]> {

    private final Provider<?> provider;
    private final Class<?> type;

    public ArrayProvider(Provider<?> provider, Class<T> type) {
        super(ProviderType.ARGUMENT, provider.getDefaultName() + "s");

        if (type.isPrimitive()) {
            throw new IllegalArgumentException("Primitive array parameters are not supported.");
        }

        this.provider = provider;
        this.type = type;
    }

    @Override
    @SuppressWarnings("unchecked")
    public T[] provide(CommandExecution execution, CommandArgument argument) {
        String[] arguments = argument.getValue().split(" ");
        T[] values = (T[]) Array.newInstance(type, arguments.length);

        for (int i = 0; i < arguments.length; i++) {
            values[i] = (T) provider.provide(execution,
                new CommandArgument(arguments[i], argument.getParameter())
            );
        }

        return values;
    }

    @Override
    public List<String> suggest(CommandSuggestion suggestion, CommandArgument argument) {
        return provider.suggest(suggestion, argument);
    }
}
