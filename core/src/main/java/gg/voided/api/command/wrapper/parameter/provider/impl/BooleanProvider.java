package gg.voided.api.command.wrapper.parameter.provider.impl;

import gg.voided.api.command.actor.Actor;
import gg.voided.api.command.exception.execution.ExitMessage;
import gg.voided.api.command.execution.CommandExecution;
import gg.voided.api.command.execution.argument.CommandArgument;
import gg.voided.api.command.wrapper.parameter.provider.Provider;
import gg.voided.api.command.wrapper.parameter.provider.ProviderType;

import java.util.Arrays;
import java.util.List;

/**
 * @author J4C0B3Y
 * @version CommandAPI
 * @since 27/08/2024
 */
public class BooleanProvider extends Provider<Boolean> {

    public BooleanProvider() {
        super(ProviderType.ARGUMENT);
    }

    @Override
    public Boolean flagDefault(CommandExecution execution) {
        return false;
    }

    @Override
    public Boolean provide(CommandExecution execution, CommandArgument argument) {
        try {
            return Boolean.parseBoolean(argument.getValue());
        } catch (NumberFormatException exception) {
            throw new ExitMessage("Boolean expected, '" + argument.getValue() + "' found.");
        }
    }

    @Override
    public List<String> suggest(Actor actor, String prefix) {
        return Arrays.asList("true", "false");
    }
}
