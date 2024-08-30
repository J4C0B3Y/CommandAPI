package gg.voided.api.command.wrapper;

import gg.voided.api.command.CommandHandler;
import gg.voided.api.command.actor.Actor;
import gg.voided.api.command.annotation.command.Help;
import gg.voided.api.command.annotation.command.Requires;
import gg.voided.api.command.annotation.registration.Disabled;
import gg.voided.api.command.annotation.registration.Register;
import gg.voided.api.command.exception.registration.InvalidWrapperException;
import gg.voided.api.command.utils.AnnotationUtils;
import gg.voided.api.command.utils.ListUtils;
import lombok.Getter;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author J4C0B3Y
 * @version CommandAPI
 * @since 27/08/2024
 */
@Getter
public abstract class CommandWrapper {
    private final String name;
    private final List<String> aliases;
    private final Object instance;
    private final CommandHandler handler;

    private final String description;
    private final String permission;
    private final boolean helpEnabled;

    private final Map<String, CommandHandle> handles = new HashMap<>();

    public CommandWrapper(String name, List<String> aliases, Object instance, CommandHandler handler) {
        this.name = name.toLowerCase();
        this.aliases = ListUtils.map(aliases, String::toLowerCase);
        this.instance = instance;
        this.handler = handler;

        Class<?> clazz = instance.getClass();

        if (clazz.isAnnotationPresent(Disabled.class)) {
            throw new InvalidWrapperException("Wrapper '" + clazz.getSimpleName() + "' is marked @Disabled");
        }

        this.description = AnnotationUtils.getValue(clazz, Register.class, Register::description, "");
        this.permission = AnnotationUtils.getValue(clazz, Requires.class, Requires::value, "");
        this.helpEnabled = clazz.isAnnotationPresent(Help.class);

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
            CommandHandle handle = getHandle(String.join(" ", arguments.subList(0, i + 1)));

            if (handle != null) {
                return handle;
            }
        }

        return null;
    }

    public boolean hasPermission(Actor actor) {
        return actor.hasPermission(permission);
    }

    public void dispatch(Actor actor, String label, List<String> arguments) {
        CommandHandle handle = getHandle(arguments);

        if (handle == null) {

        }
    }
}
