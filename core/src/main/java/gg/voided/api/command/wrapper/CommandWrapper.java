package gg.voided.api.command.wrapper;

import gg.voided.api.command.CommandHandler;
import gg.voided.api.command.annotation.command.Requires;
import gg.voided.api.command.annotation.registration.Description;
import gg.voided.api.command.utils.AnnotationUtils;
import gg.voided.api.command.utils.ListUtils;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

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
public class CommandWrapper {
    private final String name;
    private final List<String> aliases;
    private final Object instance;
    private final CommandHandler handler;

    private final String description;
    private final String permission;

    private final Map<String, CommandHandle> handles = new HashMap<>();

    public CommandWrapper(String name, List<String> aliases, Object instance, CommandHandler handler) {
        this.name = name.toLowerCase();
        this.aliases = ListUtils.map(aliases, String::toLowerCase);
        this.instance = instance;
        this.handler = handler;

        Class<?> clazz = instance.getClass();

        this.description = AnnotationUtils.getValue(clazz, Description.class, Description::value, "");
        this.permission = AnnotationUtils.getValue(clazz, Requires.class, Requires::value, "");

        for (Method method : clazz.getDeclaredMethods()) {
            CommandHandle handle = new CommandHandle(this, method);

        }
    }

}
