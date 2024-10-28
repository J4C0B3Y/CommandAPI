package net.j4c0b3y.api.command.wrapper;

import lombok.Getter;
import net.j4c0b3y.api.command.actor.Actor;
import net.j4c0b3y.api.command.annotation.command.Command;
import net.j4c0b3y.api.command.annotation.command.Requires;
import net.j4c0b3y.api.command.annotation.command.Usage;
import net.j4c0b3y.api.command.annotation.command.condition.Condition;
import net.j4c0b3y.api.command.annotation.parameter.Manual;
import net.j4c0b3y.api.command.exception.execution.UnknownFlagException;
import net.j4c0b3y.api.command.exception.registration.ParameterStructureException;
import net.j4c0b3y.api.command.execution.argument.CommandArgument;
import net.j4c0b3y.api.command.execution.argument.flag.CommandFlag;
import net.j4c0b3y.api.command.execution.argument.flag.FlagAction;
import net.j4c0b3y.api.command.utils.AnnotationUtils;
import net.j4c0b3y.api.command.utils.StringUtils;
import net.j4c0b3y.api.command.wrapper.suggestion.CommandSuggestion;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.function.Function;

/**
 * @author J4C0B3Y
 * @version CommandAPI
 * @since 27/08/2024
 */
@Getter
public class CommandHandle {
    private final CommandWrapper wrapper;
    private final Method method;

    private final String name;
    private final String description;
    private final List<String> aliases;
    private final boolean hidden;
    private final boolean async;

    private final List<CommandParameter> parameters = new ArrayList<>();
    private final List<Annotation> conditions;

    private final String permission;
    private final String usage;

    public CommandHandle(CommandWrapper wrapper, Method method) {
        this.wrapper = wrapper;
        this.method = method;

        Command command = method.getAnnotation(Command.class);

        this.name = command.name();
        this.description = command.description();
        this.aliases = Arrays.asList(command.aliases());
        this.hidden = command.hidden();
        this.async = command.async();

        for (Parameter parameter : method.getParameters()) {
            parameters.add(new CommandParameter(this, parameter));
        }

        List<CommandParameter> nonFlagParameters = new ArrayList<>();

        for (CommandParameter parameter : parameters) {
            if (!parameter.isFlag()) {
                nonFlagParameters.add(parameter);
            }
        }

        boolean optionalArguments = false;

        for (CommandParameter parameter : nonFlagParameters) {
            if (!parameter.isOptional() && optionalArguments) {
                throw new ParameterStructureException("Required parameter '" + parameter.getName() + "' cannot be after @Default.");
            }

            if (parameter.isLast() && nonFlagParameters.indexOf(parameter) != nonFlagParameters.size() - 1) {
                throw new ParameterStructureException("Parameter '" + parameter.getName() + "' must be the last parameter.");
            }

            if (parameter.isOptional()) {
                optionalArguments = true;
            }
        }

        this.conditions = AnnotationUtils.getSpecial(method.getAnnotations(), Condition.class);
        this.permission = AnnotationUtils.getValue(method, Requires.class, Requires::value, "");
        this.usage = AnnotationUtils.getValue(method, Usage.class, Usage::value, generateUsage());
    }

    public String getFullName() {
        String space = !name.isEmpty() ? " " : "";
        return wrapper.getName() + space + name;
    }

    public boolean hasDescription() {
        return !description.isEmpty();
    }

    public String generateUsage() {
        List<String> arguments = new ArrayList<>();
        List<CommandParameter> flags = new ArrayList<>();

        for (CommandParameter parameter : parameters) {
            if (parameter.isFlag()) {
                flags.add(parameter);
                continue;
            }

            if (!parameter.getProvider().isConsumer()) {
                continue;
            }

            String left = parameter.isOptional() ? "[" : "<";
            String right = parameter.isOptional() ? "]" : ">";

            arguments.add(left + parameter.getName() + right);
        }

        for (CommandParameter parameter : flags) {
            arguments.add(CommandFlag.getFlag(parameter.getFlagNames().get(0)));

            if (!parameter.isBoolean()) {
                arguments.add("<" + parameter.getName() + ">");
            }
        }

        return String.join(" ", arguments);
    }

    public void invoke(Actor actor, String label, List<Object> arguments) {
        wrapper.getHandler().runTask(() ->
            wrapper.handleExceptions(actor, this, label, () ->
                method.invoke(wrapper.getObject(), arguments.toArray())
            ),
            async
        );
    }

    public List<String> matches(Function<String, Boolean> matcher) {
        List<String> matches = new ArrayList<>();

        if (matcher.apply(name)) {
            matches.add(name);
        }

        for (String alias : aliases) {
            if (matcher.apply(alias)) {
                matches.add(alias);
            }
        }

        return matches;
    }

    public String getLabel(List<String> arguments) {
        for (int i = arguments.size(); i >= 0; i--) {
            String label = String.join(" ", arguments.subList(0, i)).toLowerCase();

            if (label.equalsIgnoreCase(name)) {
                return name;
            }

            for (String alias : aliases) {
                if (label.equalsIgnoreCase(alias)) {
                    return alias;
                }
            }
        }

        return null;
    }

    public List<String> stripLabel(List<String> arguments) {
        String label = getLabel(arguments);

        if (label.isEmpty()) {
            return arguments;
        }

        int length = label.split(" ").length;
        return arguments.subList(length, arguments.size());
    }

    public CommandParameter getFlag(String name) {
        for (CommandParameter parameter : parameters) {
            if (parameter.isFlag() && parameter.getFlagNames().contains(name)) {
                return parameter;
            }
        }

        return null;
    }

    public List<String> suggest(Actor actor, List<String> arguments) {
        if (arguments.isEmpty()) {
            return Collections.emptyList();
        }

        String prefix = arguments.get(arguments.size() - 1);
        List<String> suggestions = new ArrayList<>();

        // Flag value suggestions

        if (arguments.size() >= 2) {
            try {
                String flag = CommandFlag.validate(arguments.get(arguments.size() - 2), wrapper.getHandler());

                if (flag != null) {
                    CommandParameter parameter = getFlag(flag);

                    if (parameter != null && !parameter.isBoolean()) {
                        suggestions.addAll(parameter.getProvider().suggest(
                            new CommandSuggestion(actor, arguments),
                            new CommandArgument(prefix, parameter)
                        ));

                        return suggestions;
                    }
                }
            } catch (UnknownFlagException ignored) {
            }
        }

        // Flag name suggestions
        if (prefix.startsWith("-")) {
            for (CommandParameter parameter : this.parameters) {
                if (!parameter.isFlag()) continue;

                for (String name : parameter.getFlagNames()) {
                    String flag = CommandFlag.getFlag(name);

                    if (flag.startsWith(prefix)) {
                        suggestions.add(flag);
                    }
                }
            }
        }

        // Calculate flag offset

        int offset = 0;
        boolean ignore = wrapper.getHandler().getUnknownFlagAction() == FlagAction.ARGUMENT;

        for (int i = 0; i < arguments.size() - 1; i++) {
            try {
                String flag = CommandFlag.validate(arguments.get(i), wrapper.getHandler());
                if (flag == null) continue;
                if (!ignore) offset++;

                CommandParameter parameter = getFlag(flag);
                if (parameter == null) continue;
                if (ignore) offset++;

                if (parameter.isBoolean() || i + 1 >= arguments.size() - 1) {
                    continue;
                }

                String next = CommandFlag.validate(arguments.get(i + 1), wrapper.getHandler());

                if (next == null || getFlag(next) == null) {
                    offset++;
                }
            } catch (UnknownFlagException exception) {
                if (wrapper.getHandler().getUnknownFlagAction() != FlagAction.ARGUMENT) {
                    offset++;
                }
            }
        }

        // Argument suggestions

        List<CommandParameter> parameters = new ArrayList<>();

        for (CommandParameter parameter : this.parameters) {
            if (parameter.getProvider().isConsumer() && !parameter.isFlag()) {
                parameters.add(parameter);
            }
        }

        int parameterIndex = arguments.size() - offset - 1;

        if (parameterIndex >= parameters.size()) {
            return suggestions;
        }

        CommandParameter parameter = parameters.get(parameterIndex);
        Manual manual = parameter.getAnnotation(Manual.class);

        if (manual == null || (!manual.value().isEmpty() && actor.hasPermission(manual.value()))) {
            suggestions.addAll(parameter.getProvider().suggest(
                new CommandSuggestion(actor, arguments),
                new CommandArgument(prefix, parameter)
            ));
        }

        suggestions.removeIf(suggestion -> !StringUtils.startsWithIgnoreCase(suggestion, prefix));

        return suggestions;
    }
}
