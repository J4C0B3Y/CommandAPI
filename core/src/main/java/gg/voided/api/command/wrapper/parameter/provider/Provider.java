package gg.voided.api.command.wrapper.parameter.provider;

import gg.voided.api.command.actor.Actor;
import gg.voided.api.command.execution.CommandExecution;
import gg.voided.api.command.execution.argument.CommandArgument;
import lombok.Getter;

import java.util.Collections;
import java.util.List;

/**
 * @author J4C0B3Y
 * @version CommandAPI
 * @since 27/08/2024
 */
@Getter
public abstract class Provider<T> {
    private final boolean consumer;

    public Provider(ProviderType type) {
        this.consumer = type.isConsumer();
    }

    public T flagDefault(CommandExecution execution) {
        return null;
    }

    public boolean isAsync() {
        return false;
    }

    public abstract T provide(CommandExecution execution, CommandArgument argument);

    public List<String> suggest(Actor actor) {
        return Collections.emptyList();
    }
}
