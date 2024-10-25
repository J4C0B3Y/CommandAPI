package net.j4c0b3y.api.command.wrapper.parameter.provider.impl.argument;

import net.j4c0b3y.api.command.exception.execution.ExitMessage;
import net.j4c0b3y.api.command.execution.CommandExecution;
import net.j4c0b3y.api.command.execution.argument.CommandArgument;
import net.j4c0b3y.api.command.wrapper.parameter.provider.Provider;
import net.j4c0b3y.api.command.wrapper.parameter.provider.ProviderType;

/**
 * @author J4C0B3Y
 * @version CommandAPI
 * @since 27/08/2024
 */
public class CharacterProvider extends Provider<Character> {

    public CharacterProvider() {
        super(ProviderType.ARGUMENT, "character");
    }

    @Override
    public Character provide(CommandExecution execution, CommandArgument argument) {
        String value = argument.getValue();

        if (value.length() > 1) {
            throw new ExitMessage(execution.getHandler().getLocale()
                .getInvalidType("character", argument.getValue())
            );
        }

        return value.charAt(0);
    }
}
