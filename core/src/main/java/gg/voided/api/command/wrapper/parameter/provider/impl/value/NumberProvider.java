package gg.voided.api.command.wrapper.parameter.provider.impl.value;

import gg.voided.api.command.exception.execution.ExitMessage;
import gg.voided.api.command.execution.CommandExecution;
import gg.voided.api.command.execution.argument.CommandArgument;
import gg.voided.api.command.wrapper.parameter.provider.Provider;
import gg.voided.api.command.wrapper.parameter.provider.ProviderType;

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
