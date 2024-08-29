package gg.voided.api.command.execution;

import gg.voided.api.command.CommandHandler;
import gg.voided.api.command.actor.Actor;
import gg.voided.api.command.exception.execution.InvalidArgumentException;
import gg.voided.api.command.execution.argument.CommandArgument;
import gg.voided.api.command.execution.argument.UnknownFlagAction;
import gg.voided.api.command.wrapper.CommandHandle;
import gg.voided.api.command.wrapper.parameter.CommandParameter;
import lombok.Getter;

import java.util.*;

/**
 * @author J4C0B3Y
 * @version CommandAPI
 * @since 27/08/2024
 */
@Getter
public class CommandExecution {
    private final Actor actor;
    private final CommandHandle handle;
    private final String label;
    private final List<String> arguments;
    private final CommandHandler handler;

    private final List<ProvidedParameter> providedParameters = new ArrayList<>();

    public CommandExecution(Actor actor, CommandHandle handle, String label, List<String> arguments) {
        this.actor = actor;
        this.handle = handle;
        this.label = label;
        this.arguments = arguments;
        this.handler = handle.getWrapper().getHandler();

        for (CommandParameter parameter : handle.getParameters()) {
            providedParameters.add(new ProvidedParameter(parameter));
        }
    }

    public void execute() {
        List<String> arguments = new ArrayList<>(this.arguments);
        int flags = 0;

        for (int index = 0; index < providedParameters.size(); index++) {
            ProvidedParameter providedParameter = providedParameters.get(index);
            CommandParameter parameter = providedParameter.getParameter();

            // If the parameter is @Text, combine the remaining arguments into one string.
            if (parameter.isText()) {
                arguments = combineRemaining(arguments, index);
            }

            if (parameter.isFlag()) {
                if (providedParameter.getArgument() == null) {
                    providedParameter.provide(parameter.getProvider());
                }

                flags++;
                continue;
            }

        }
    }

    private List<String> combineRemaining(List<String> arguments, int startIndex) {
        List<String> previous = new ArrayList<>(arguments.subList(0, startIndex));
        String combined = String.join(" ", arguments.subList(startIndex, arguments.size()));

        if (!combined.isEmpty()) {
            previous.add(combined);
        }

        return previous;
    }

    private List<String> extractFlags(List<String> arguments) {
        Map<String, ProvidedParameter> flags = new HashMap<>();
        UnknownFlagAction action = handler.getUnknownFlagAction();
        Iterator<String> iterator = arguments.iterator();

        for (ProvidedParameter providedParameter : providedParameters) {
            CommandParameter parameter = providedParameter.getParameter();

            if (parameter.isFlag()) {
                for (String flag : parameter.getFlagNames()) {
                    flags.put(flag, providedParameter);
                }
            }
        }

        while (iterator.hasNext()) {
            String argument = iterator.next();

            if (!CommandArgument.isFlag(argument, action)) {
                continue;
            }

            ProvidedParameter parameter = flags.get(CommandArgument.getFlagName(argument));

            if (parameter == null) {
                if (action.equals(UnknownFlagAction.ERROR)) {
                    throw new InvalidArgumentException("Unrecognised flag '" + argument + "'.");
                }

                if (action.equals(UnknownFlagAction.IGNORE)) {
                    iterator.remove();
                }

                continue;
            }

            if (parameter.getArgument() != null) {
                throw new InvalidArgumentException("Flag '" + argument + "' has already been specified.");
            }

            if (parameter.getParameter().isBoolean()) {
                parameter.setArgument("true");
                parameter.provide(true);
                continue;
            }

            if (!iterator.hasNext()) {
                throw new InvalidArgumentException("Flag '" + argument + "' requires a value.");
            }

            iterator.remove();
            String value = iterator.next();

            if (!CommandArgument.isFlag(value, action)) {
                throw new InvalidArgumentException("Flag '" + argument + "' requires a value.");
            }

            parameter.setArgument(value);
            iterator.remove();
        }

        return arguments;
    }
}
