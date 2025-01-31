package net.j4c0b3y.api.command.wrapper.binding.provider.impl.argument;

import net.j4c0b3y.api.command.exception.execution.ExitMessage;
import net.j4c0b3y.api.command.execution.CommandExecution;
import net.j4c0b3y.api.command.execution.argument.CommandArgument;
import net.j4c0b3y.api.command.wrapper.binding.provider.Provider;
import net.j4c0b3y.api.command.wrapper.binding.provider.ProviderType;
import net.j4c0b3y.api.command.wrapper.suggestion.CommandSuggestion;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author J4C0B3Y
 * @version CommandAPI
 * @since 1/02/2025
 */
public class MapProvider<T, M extends Map<String, T>> extends Provider<T> {

    private final M map;
    private final String keyName;

    public MapProvider(M map, String keyName, String valueName) {
        super(ProviderType.ARGUMENT, valueName);
        this.map = map;
        this.keyName = keyName;
    }

    @Override
    public T provide(CommandExecution execution, CommandArgument argument) {
        T value = map.get(argument.getValue());

        if (value == null) {
            throw new ExitMessage(execution.getHandler().getLocale()
                .getMapEntryNotFound(argument.getValue(), keyName, getDefaultName())
            );
        }

        return value;
    }

    @Override
    public List<String> suggest(CommandSuggestion suggestion, CommandArgument argument) {
        return new ArrayList<>(map.keySet());
    }
}
