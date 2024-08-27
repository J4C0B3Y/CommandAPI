package gg.voided.api.command.wrapper.parameter.provider;

import gg.voided.api.command.annotation.provider.Async;
import gg.voided.api.command.exception.execution.ExitMessage;
import gg.voided.api.command.execution.CommandExecution;
import gg.voided.api.command.execution.argument.CommandArgument;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Collections;
import java.util.List;

/**
 * @author J4C0B3Y
 * @version CommandAPI
 * @since 8/27/24
 */
@Getter
@RequiredArgsConstructor
public abstract class Provider<T> {
    private final boolean async = getClass().isAnnotationPresent(Async.class);
    private final boolean consumer;

    public abstract T provide(CommandExecution execution, CommandArgument argument) throws ExitMessage;

    public List<String> suggest(String prefix) {
        return Collections.emptyList();
    }
}
