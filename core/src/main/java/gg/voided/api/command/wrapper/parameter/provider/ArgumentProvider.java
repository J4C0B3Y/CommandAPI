package gg.voided.api.command.wrapper.parameter.provider;

import gg.voided.api.command.execution.CommandExecution;

/**
 * @author J4C0B3Y
 * @version CommandAPI
 * @since 8/27/24
 */
public abstract class ArgumentProvider<T> extends Provider<T> {

    public ArgumentProvider() {
        super(true);
    }

    public T flagDefault(CommandExecution execution) {
        return null;
    }
}
