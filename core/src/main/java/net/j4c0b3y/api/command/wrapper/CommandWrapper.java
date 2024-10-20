package net.j4c0b3y.api.command.wrapper;

import lombok.AccessLevel;
import lombok.Getter;
import net.j4c0b3y.api.command.CommandHandler;
import net.j4c0b3y.api.command.actor.Actor;
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
            CommandHandle handle = new CommandHandle(this, method);

            if (handles.containsKey(handle.getName())) {
                throw new InvalidWrapperException("Duplicate handle '" + handle.getName() + "'.");
            }

            handles.put(handle.getName(), handle);
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
            if (handle.getAliases().contains(label)) {
                return handle;
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

        if (handle == null || !actor.hasPermission(handle.getPermission())) {
            return suggestHandles(actor, arguments);
        }

        return handle.suggest(actor, handle.stripLabel(arguments));
    }

    private List<String> suggestHandles(Actor actor, List<String> arguments) {
        List<String> suggestions = new ArrayList<>();

        for (CommandHandle handle : handles.values()) {
            List<String> matches = handle.matches(label -> label.startsWith(
                String.join(" ", arguments).toLowerCase()
            ));

            if (matches.isEmpty() || !actor.hasPermission(handle.getPermission())) {
                continue;
            }

            for (String match : matches) {
                suggestions.add(match.split(" ")[arguments.size() - 1]);
            }
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
                actor.sendMessage(throwable.getMessage());

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
