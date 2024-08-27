package gg.voided.api.command.wrapper.parameter.provider;

import gg.voided.api.command.execution.CommandExecution;

import java.util.Collections;
import java.util.List;

/**
 * @author J4C0B3Y
 * @version CommandAPI
 * @since 27/08/2024
 */
public abstract class ArgumentProvider<T> extends Provider<T> {

    public ArgumentProvider() {
        super(true);
    }

    public T flagDefault(CommandExecution execution) {
        return null;
    }

    public List<String> suggest(String prefix) {
        return Collections.emptyList();
    }
}
