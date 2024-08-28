package gg.voided.api.command.wrapper.parameter.provider.impl;

import gg.voided.api.command.exception.execution.ExitMessage;
import gg.voided.api.command.execution.CommandExecution;
import gg.voided.api.command.execution.argument.CommandArgument;
import gg.voided.api.command.wrapper.parameter.provider.ArgumentProvider;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * @author J4C0B3Y
 * @version CommandAPI
 * @since 27/08/2024
 */
public class BooleanProvider extends ArgumentProvider<Boolean> {

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
    public List<String> suggest(String prefix) {
        prefix = prefix.toLowerCase();

        if (prefix.isEmpty()) {
            return Arrays.asList("true", "false");
        }

        if ("true".startsWith(prefix)) {
            return Collections.singletonList("true");
        }

        if ("false".startsWith(prefix)) {
            return Collections.singletonList("false");
        }

        return Collections.emptyList();
    }
}