package net.j4c0b3y.api.command.wrapper;

import lombok.AccessLevel;
import lombok.Getter;
import net.j4c0b3y.api.command.CommandHandler;
import net.j4c0b3y.api.command.actor.Actor;
import net.j4c0b3y.api.command.annotation.command.Command;
import net.j4c0b3y.api.command.annotation.command.Help;
import net.j4c0b3y.api.command.annotation.command.Requires;
import net.j4c0b3y.api.command.annotation.registration.Ignore;
import net.j4c0b3y.api.command.annotation.registration.Register;
import net.j4c0b3y.api.command.exception.execution.ExitMessage;
import net.j4c0b3y.api.command.exception.registration.InvalidWrapperException;
import net.j4c0b3y.api.command.execution.CommandExecution;
import net.j4c0b3y.api.command.utils.AnnotationUtils;
import net.j4c0b3y.api.command.utils.CheckedRunnable;
import net.j4c0b3y.api.command.utils.ListUtils;
import net.j4c0b3y.api.command.utils.StringUtils;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;

/**
 * @author J4C0B3Y
 * @version CommandAPI
 * @since 27/08/2024
 */
@Getter
public abstract class CommandWrapper {
    private final String name;
    private final List<String> aliases;
    private final Object object;
    private final CommandHandler handler;

    private final String description;
    private final String permission;

    @Getter(AccessLevel.NONE)
    private final Help help;

    private final Map<String, CommandHandle> handles = new HashMap<>();

    public CommandWrapper(Object object, String name, List<String> aliases, CommandHandler handler) {
        this.name = name.toLowerCase();
        this.aliases = ListUtils.map(aliases, String::toLowerCase);
        this.object = object;
        this.handler = handler;

        Class<?> clazz = object.getClass();

        if (clazz.isAnnotationPresent(Ignore.class)) {
            throw new InvalidWrapperException("Wrapper '" + clazz.getSimpleName() + "' is marked @Disabled");
        }

        this.description = AnnotationUtils.getValue(clazz, Register.class, Register::description, "");
        this.permission = AnnotationUtils.getValue(clazz, Requires.class, Requires::value, "");
        this.help = clazz.getAnnotation(Help.class);

        for (Method method : clazz.getDeclaredMethods()) {
            if (!method.isAnnotationPresent(Command.class)) continue;

            CommandHandle handle = new CommandHandle(this, method);

            if (handles.containsKey(handle.getName().toLowerCase())) {
                throw new InvalidWrapperException("Duplicate handle '" + handle.getName() + "'.");
            }

            handles.put(handle.getName().toLowerCase(), handle);
        }

        if (handles.isEmpty()) {
            handler.getLogger().warning("Wrapper '" + clazz.getSimpleName() + "' doesn't contain any handles.");
        }
    }

    public abstract void register();

    public boolean isHelp() {
        return help != null;
    }

    public CommandHandle getHandle(String label) {
        label = label.toLowerCase();

        if (handles.containsKey(label)) {
            return handles.get(label);
        }

        for (CommandHandle handle : handles.values()) {
            for (String alias : handle.getAliases()) {
                if (alias.equalsIgnoreCase(label)) {
                    return handle;
                }
            }
        }

        return null;
    }

    public CommandHandle getHandle(List<String> arguments) {
        for (int i = arguments.size(); i >= 0; i--) {
            CommandHandle handle = getHandle(String.join(" ", arguments.subList(0, i)));

            if (handle != null) {
                return handle;
            }
        }

        return null;
    }

    public void dispatch(Actor actor, String label, List<String> arguments) {
        CommandHandle handle = getHandle(arguments);

        handleExceptions(actor, handle, label, () ->
            dispatch(actor, handle, label, arguments)
        );
    }

    private void dispatch(Actor actor, CommandHandle handle, String label, List<String> arguments) {
        boolean help = !arguments.isEmpty() && arguments.get(0).equalsIgnoreCase("help");

        if (handle == null || (help && isHelp() && !this.help.ignore())) {
            if (isHelp() && handler.getUsageHandler().sendHelp(actor, this, label, arguments)) {
                return;
            }

            handler.getLocale().getInvalidSubcommand(label, isHelp()).forEach(actor::sendMessage);
            return;
        }

        if (!actor.hasPermission(handle.getPermission())) {
            handler.getLocale().getNoPermission().forEach(actor::sendMessage);
            return;
        }

        new CommandExecution(actor, handle, label, handle.stripLabel(arguments)).execute();
    }

    public List<String> suggest(Actor actor, List<String> arguments) {
        CommandHandle handle = getHandle(arguments);
        List<String> suggestions = new ArrayList<>();

        boolean suggestHandles = handle == null || !actor.hasPermission(handle.getPermission());
        boolean rootCommand = handle != null && handle.getName().isEmpty();

        if (suggestHandles || rootCommand) {
            suggestions.addAll(suggestHandles(actor, arguments));
        }

        if (!suggestHandles || rootCommand) {
            suggestions.addAll(handle.suggest(actor, handle.stripLabel(arguments)));
        }

        if (suggestions.size() == 1 && suggestions.get(0).isEmpty()) {
            // https://bugs.mojang.com/browse/MC-165562
            suggestions.set(0, " ");
        }

        return suggestions;
    }

    private List<String> suggestHandles(Actor actor, List<String> arguments) {
        List<String> suggestions = new ArrayList<>();

        for (CommandHandle handle : handles.values()) {
            if (handle.isHidden() || !actor.hasPermission(handle.getPermission())) {
                continue;
            }

            List<String> matches = handle.matches(label ->
                StringUtils.startsWithIgnoreCase(label, String.join(" ", arguments))
            );

            if (matches.isEmpty()) {
                continue;
            }

            for (String match : matches) {
                suggestions.add(match.split(" ")[arguments.size() - 1]);
            }
        }

        if (isHelp() && !this.help.ignore()) {
            suggestions.add("help");
        }

        return suggestions;
    }

    public void handleExceptions(Actor actor, CommandHandle handle, String label, CheckedRunnable task) {
        try {
            task.run();
        } catch (Exception exception) {
            Throwable throwable = exception;

            while (throwable instanceof InvocationTargetException) {
                throwable = throwable.getCause();
            }

            if (throwable instanceof ExitMessage) {
                if (throwable.getMessage() != null) {
                    actor.sendMessage(throwable.getMessage());
                }

                if (((ExitMessage) throwable).isShowUsage()) {
                    handler.getUsageHandler().sendUsage(actor, handle, label);
                }

                return;
            }

            handler.getLocale().getExceptionOccurred().forEach(actor::sendMessage);

            handler.getLogger().log(Level.SEVERE,
                "Failed to execute command '/" +
                label + " " + handle.getName() + "'.",
                throwable
            );
        }
    }
}
