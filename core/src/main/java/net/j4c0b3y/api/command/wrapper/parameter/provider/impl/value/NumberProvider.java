package net.j4c0b3y.api.command.wrapper.parameter.provider.impl.value;

import net.j4c0b3y.api.command.exception.execution.ExitMessage;
import net.j4c0b3y.api.command.execution.CommandExecution;
import net.j4c0b3y.api.command.execution.argument.CommandArgument;
import net.j4c0b3y.api.command.wrapper.parameter.provider.Provider;
import net.j4c0b3y.api.command.wrapper.parameter.provider.ProviderType;

import java.util.function.Function;

/**
 * @author J4C0B3Y
 * @version CommandAPI
 * @since 20/10/2024
 */
public class NumberProvider<T extends Number> extends Provider<T> {
    private final Function<String, T> parser;
    private final String type;
    private final T flagDefault;

    public NumberProvider(Function<String, T> parser, String type, T flagDefault) {
        super(ProviderType.ARGUMENT);
        this.parser = parser;
        this.type = type;
        this.flagDefault = flagDefault;
    }

    @Override
    public T flagDefault(CommandExecution execution) {
        return flagDefault;
    }

    @Override
    public T provide(CommandExecution execution, CommandArgument argument) {
        try {
            return parser.apply(argument.getValue());
        } catch (NumberFormatException exception) {
            throw new ExitMessage(execution.getHandler().getLocale()
                .getInvalidType(type, argument.getValue())
            );
        }
    }
}
