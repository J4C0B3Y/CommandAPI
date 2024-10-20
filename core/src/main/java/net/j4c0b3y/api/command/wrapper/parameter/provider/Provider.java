package net.j4c0b3y.api.command.wrapper.parameter.provider;

import lombok.Getter;
import net.j4c0b3y.api.command.actor.Actor;
import net.j4c0b3y.api.command.execution.CommandExecution;
import net.j4c0b3y.api.command.execution.argument.CommandArgument;

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
