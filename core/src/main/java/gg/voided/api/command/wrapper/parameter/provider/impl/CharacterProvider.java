package gg.voided.api.command.wrapper.parameter.provider.impl;

import gg.voided.api.command.exception.execution.ExitMessage;
import gg.voided.api.command.execution.CommandExecution;
import gg.voided.api.command.execution.argument.CommandArgument;
import gg.voided.api.command.wrapper.parameter.provider.ArgumentProvider;

/**
 * @author J4C0B3Y
 * @version CommandAPI
 * @since 27/08/2024
 */
public class CharacterProvider extends ArgumentProvider<Character> {

    @Override
    public Character provide(CommandExecution execution, CommandArgument argument) {
        String value = argument.getValue();

        if (value.length() > 1) {
            throw new ExitMessage("Character expected, '" + value + "' found.");
        }

        return value.charAt(0);
    }
}
