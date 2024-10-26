package net.j4c0b3y.api.command.wrapper.binding.provider.impl.argument;

import net.j4c0b3y.api.command.exception.execution.ExitMessage;
import net.j4c0b3y.api.command.execution.CommandExecution;
import net.j4c0b3y.api.command.execution.argument.CommandArgument;
import net.j4c0b3y.api.command.utils.StringUtils;
import net.j4c0b3y.api.command.wrapper.binding.provider.Provider;
import net.j4c0b3y.api.command.wrapper.binding.provider.ProviderType;
import net.j4c0b3y.api.command.wrapper.suggestion.CommandSuggestion;

import java.util.ArrayList;
import java.util.List;

/**
 * @author J4C0B3Y
 * @version CommandAPI
 * @since 20/10/2024
 */
public class EnumProvider<T extends Enum<T>> extends Provider<T> {
    private final Class<T> type;

    public EnumProvider(Class<T> type, String name) {
        super(ProviderType.ARGUMENT, name);
        this.type = type;
    }

    public EnumProvider(Class<T> type) {
        this(type, StringUtils.decapitalize(type.getSimpleName()));
    }

    @Override
    public T provide(CommandExecution execution, CommandArgument argument) {
        String name = argument.getValue();

        for (T constant : type.getEnumConstants()) {
            if (constant.name().equalsIgnoreCase(name)) {
                return constant;
            }
        }

        throw new ExitMessage(execution.getHandler().getLocale()
            .getInvalidEnum(name, suggest(null, null))
        );
    }

    @Override
    public List<String> suggest(CommandSuggestion suggestion, CommandArgument argument) {
        List<String> suggestions = new ArrayList<>();

        for (T constant : type.getEnumConstants()) {
            suggestions.add(constant.name());
        }

        return suggestions;
    }
}
