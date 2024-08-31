package gg.voided.api.command.wrapper;


import gg.voided.api.command.actor.Actor;
import gg.voided.api.command.annotation.command.Command;
import gg.voided.api.command.annotation.command.Requires;
import gg.voided.api.command.annotation.command.Usage;
import gg.voided.api.command.exception.registration.InvalidHandleException;
import gg.voided.api.command.exception.registration.ParameterStructureException;
import gg.voided.api.command.execution.argument.CommandArgument;
import gg.voided.api.command.utils.AnnotationUtils;
import gg.voided.api.command.utils.ListUtils;
import gg.voided.api.command.wrapper.parameter.CommandParameter;
import lombok.Getter;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.ArrayList;
import java.util.List;

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

    private final String permission;
    private final String usage;

    public CommandHandle(CommandWrapper wrapper, Method method) {
        this.wrapper = wrapper;
        this.method = method;

        Command command = method.getAnnotation(Command.class);

        if (command == null) {
            throw new InvalidHandleException("Method '" + method.getName() + "' must be annotated with @Command.");
        }

        this.name = command.name().toLowerCase();
        this.description = command.description();
        this.aliases = ListUtils.map(command.aliases(), String::toLowerCase);
        this.hidden = command.hidden();
        this.async = command.async();

        for (Parameter parameter : method.getParameters()) {
            parameters.add(new CommandParameter(this, parameter));
        }

        boolean optionalArguments = false;

        for (CommandParameter parameter : parameters) {
            if (!parameter.isOptional() && optionalArguments) {
                throw new ParameterStructureException("Required parameter '" + parameter.getName() + "' cannot be after @Default.");
            }

            if (parameter.isLast() && parameters.indexOf(parameter) != parameters.size() - 1) {
                throw new ParameterStructureException("Parameter '" + parameter.getName() + "' must be the last parameter.");
            }

            if (parameter.isOptional()) {
                optionalArguments = true;
            }
        }

        this.permission = AnnotationUtils.getValue(method, Requires.class, Requires::value, "");
        this.usage = AnnotationUtils.getValue(method, Usage.class, Usage::value, generateUsage());
    }

    public String generateUsage() {
        List<String> arguments = new ArrayList<>();
        List<String> flags = new ArrayList<>();

        for (CommandParameter parameter : parameters) {
            if (parameter.isFlag()) {
                flags.add(parameter.getFlagNames().get(0));
                continue;
            }

            if (!parameter.getProvider().isConsumer()) {
                continue;
            }

            String left = parameter.isOptional() ? "[" : "<";
            String right = parameter.isOptional() ? "]" : ">";

            arguments.add(left + parameter.getName() + right);
        }

        for (String flag : flags) {
            arguments.add(CommandArgument.getFlagArgument(flag));
        }

        return String.join(" ", arguments);
    }

    public void invoke(Actor actor, List<Object> arguments) {
        wrapper.getHandler().runTask(() -> wrapper.handleExceptions(actor, this, () ->
            method.invoke(wrapper.getObject(), arguments.toArray())
        ), async);
    }
}
