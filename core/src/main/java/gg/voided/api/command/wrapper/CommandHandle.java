package gg.voided.api.command.wrapper;


import gg.voided.api.command.annotation.command.Command;
import lombok.Getter;

import java.lang.reflect.Method;
import java.util.List;

/**
 * @author J4C0B3Y
 * @version CommandAPI
 * @since 27/08/2024
 */
@Getter
public class CommandHandle {
    private final String name;
    private final List<String> aliases;
    private final CommandWrapper wrapper;
    private final Method method;

    public CommandHandle(CommandWrapper wrapper, Method method) {
        this.wrapper = wrapper;
        this.method = method;

        Command command = method.getAnnotation(Command.class);

        this.name =
    }

    private void validate() {

    }
}
