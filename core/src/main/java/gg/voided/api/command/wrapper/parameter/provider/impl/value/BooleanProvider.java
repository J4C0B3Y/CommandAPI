package gg.voided.api.command.wrapper.parameter.provider.impl.value;

import gg.voided.api.command.actor.Actor;
import gg.voided.api.command.exception.execution.ExitMessage;
import gg.voided.api.command.execution.CommandExecution;
import gg.voided.api.command.execution.argument.CommandArgument;
import gg.voided.api.command.wrapper.parameter.provider.Provider;
import gg.voided.api.command.wrapper.parameter.provider.ProviderType;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author J4C0B3Y
 * @version CommandAPI
 * @since 27/08/2024
 */
public class BooleanProvider extends Provider<Boolean> {
    private final static List<String> TRUE_VALUES = Arrays.asList("true", "yes");
    private final static List<String> FALSE_VALUES = Arrays.asList("false", "no");

    public BooleanProvider() {
        super(ProviderType.ARGUMENT);
    }

    @Override
    public Boolean flagDefault(CommandExecution execution) {
        return false;
    }

    @Override
    public Boolean provide(CommandExecution execution, CommandArgument argument) {
        String lowered = argument.getValue().toLowerCase();

        for (String value : TRUE_VALUES) {
            if (lowered.equals(value)) {
                return true;
            }
        }

        for (String value : FALSE_VALUES) {
            if (lowered.equals(value)) {
                return false;
            }
        }

        throw new ExitMessage(execution.getHandler().getLocale()
            .getInvalidType("boolean", argument.getValue())
        );
    }

    @Override
    public List<String> suggest(Actor actor) {
        List<String> suggestions = new ArrayList<>();

        suggestions.addAll(TRUE_VALUES);
        suggestions.addAll(FALSE_VALUES);

        return suggestions;
    }
}
