package gg.voided.api.command.wrapper.parameter.provider;

import gg.voided.api.command.exception.execution.ExitMessage;
import gg.voided.api.command.execution.CommandExecution;
import gg.voided.api.command.execution.argument.CommandArgument;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * @author J4C0B3Y
 * @version CommandAPI
 * @since 27/08/2024
 */
@Getter
@RequiredArgsConstructor
public abstract class Provider<T> {
    private final boolean consumer;
    private final boolean async;

    public abstract T provide(CommandExecution execution, CommandArgument argument) throws ExitMessage;
}
