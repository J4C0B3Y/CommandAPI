package gg.voided.api.command.wrapper;

import gg.voided.api.command.CommandHandler;
import gg.voided.api.command.actor.Actor;
import gg.voided.api.command.annotation.command.Help;
import gg.voided.api.command.annotation.command.Requires;
import gg.voided.api.command.annotation.registration.Ignore;
import gg.voided.api.command.annotation.registration.Register;
import gg.voided.api.command.exception.execution.ExitMessage;
import gg.voided.api.command.exception.registration.InvalidWrapperException;
import gg.voided.api.command.execution.CommandExecution;
import gg.voided.api.command.utils.AnnotationUtils;
import gg.voided.api.command.utils.CheckedRunnable;
import gg.voided.api.command.utils.ListUtils;
import lombok.Getter;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
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
    private final boolean help;

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
        this.help = clazz.isAnnotationPresent(Help.class);

        for (Method method : clazz.getDeclaredMethods()) {
            CommandHandle handle = new CommandHandle(this, method);

            if (handles.containsKey(handle.getName())) {
                throw new InvalidWrapperException("Duplicate handle name '" + handle.getName() + "'.");
            }

            handles.put(handle.getName(), handle);
        }
    }

    public abstract void register();

    public CommandHandle getHandle(String label) {
        label = label.toLowerCase();

        if (handles.containsKey(label)) {
            return handles.get(label);
        }

        for (CommandHandle handle : handles.values()) {{
            if (handle.getAliases().contains(label)) {
                return handle;
            }
        }}

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
        if (handle == null) {
            if (isHelp() && handler.getUsageHandler().sendHelp(actor, this, arguments)) {
                return;
            }

            actor.sendMessage("no matching subcommand found.");
            return;
        }

        if (!actor.hasPermission(handle.getPermission())) {
            actor.sendMessage("No permission!");
            return;
        }

        // Remove the handle name from the arguments
        int nameLength = handle.getName().split(" ").length;
        arguments = arguments.subList(nameLength, arguments.size());

        new CommandExecution(actor, handle, label, arguments).execute();
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

            actor.sendMessage("oops );");
            handler.getLogger().log(Level.SEVERE, "error idk", exception);
        }
    }
}
