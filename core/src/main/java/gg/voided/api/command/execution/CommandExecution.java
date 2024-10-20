package gg.voided.api.command.execution;

import gg.voided.api.command.CommandHandler;
import gg.voided.api.command.actor.Actor;
import gg.voided.api.command.exception.execution.ExitMessage;
import gg.voided.api.command.exception.execution.UnknownFlagException;
import gg.voided.api.command.execution.argument.CommandArgument;
import gg.voided.api.command.execution.argument.flag.CommandFlag;
import gg.voided.api.command.wrapper.CommandHandle;
import gg.voided.api.command.wrapper.parameter.CommandParameter;
import gg.voided.api.command.wrapper.parameter.provider.Provider;
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

        extractFlags(arguments);
        parseArguments(arguments);

        for (ProvidedParameter provided : providedParameters) {
            if (provided.isProvided()) {
                continue;
            }

            CommandParameter parameter = provided.getParameter();
            Provider<?> provider = parameter.getProvider();

            handler.runTask(() ->
                handle.getWrapper().handleExceptions(actor, handle, label, () -> {
                    provided.provide(provider.provide(this,
                        new CommandArgument(provided.getArgument(), parameter)
                    ));

                    if (provided.isProvided()) {
                        provided.provide(handler.getModifierHandler().modify(
                            provided.getValue(), this, parameter
                        ));
                    }

                    complete();
                }),
                provider.isAsync()
            );
        }
    }

    private void extractFlags(List<String> arguments) {
        Map<String, ProvidedParameter> flags = new HashMap<>();

        for (ProvidedParameter provided : providedParameters) {
            CommandParameter parameter = provided.getParameter();

            if (!parameter.isFlag()) {
                continue;
            }

            for (String flag : parameter.getFlagNames()) {
                flags.put(flag, provided);
            }
        }

        Iterator<String> iterator = arguments.iterator();

        while (iterator.hasNext()) {
            String argument = iterator.next();

            try {
                argument = CommandFlag.validate(argument);

                if (argument == null) {
                    continue;
                }

                ProvidedParameter parameter = flags.get(argument);

                if (parameter == null) {
                    throw new UnknownFlagException("Unknown flag '" + argument + "'", true);
                }

                if (parameter.getArgument() != null || parameter.isProvided()) {
                    throw new ExitMessage("Flag '" + argument + "' already specified");
                }

                if (parameter.getParameter().isBoolean()) {
                    parameter.provide(true);
                    iterator.remove();
                    continue;
                }

                if (!iterator.hasNext()) {
                    throw new ExitMessage("Flag '" + argument + "' requires a value", true);
                }

                iterator.remove();
                String value = iterator.next();

                if (CommandFlag.validate(value) != null) {
                    throw new ExitMessage("Flag '" + argument + "' requires a value", true);
                }

                parameter.setArgument(value);
                iterator.remove();
            } catch (UnknownFlagException exception) {
                switch (handler.getUnknownFlagAction()) {
                    case ERROR: throw exception;
                    case STRIP: iterator.remove();
                }
            }
        }
    }

    private void parseArguments(List<String> arguments) {
        int offset = 0;

        for (int i = 0; i < providedParameters.size(); i++) {
            ProvidedParameter provided = providedParameters.get(i);
            CommandParameter parameter = provided.getParameter();

            if (parameter.isFlag()) {
                if (!provided.isProvided()) {
                    // If no argument for a flag was given, provide its default value.
                    if (provided.getArgument() == null) {
                        provided.setArgument(parameter.getDefaultValue());
                    }

                    // If the @Default is null, use the provider's flag default.
                    if (provided.getArgument() == null) {
                        provided.provide(parameter.getProvider().flagDefault(this));
                    }
                }

                offset++;
                continue;
            }

            if (!parameter.getProvider().isConsumer()) {
                offset++;
                continue;
            }

            int adjusted = i - offset;

            if (parameter.isText()) {
                arguments = combineRemaining(arguments, adjusted);
            }

            if (adjusted >= arguments.size()) {
                if (parameter.isOptional()) {
                    provided.setArgument(parameter.getDefaultValue());
                    continue;
                }

                throw new ExitMessage(handler.getLocale().getMissingArgument(parameter.getName()), true);
            }

            provided.setArgument(arguments.get(adjusted));
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

    private void complete() {
        for (ProvidedParameter parameter : providedParameters) {
            if (!parameter.isProvided()) {
                return;
            }
        }

        List<Object> arguments = new ArrayList<>();

        for (ProvidedParameter parameter : providedParameters) {
            arguments.add(parameter.getValue());
        }

        handle.invoke(actor, label, arguments);
    }
}
