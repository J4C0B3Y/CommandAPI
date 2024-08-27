package gg.voided.api.command.wrapper;


import lombok.Getter;

import java.lang.reflect.Method;

/**
 * @author J4C0B3Y
 * @version CommandAPI
 * @since 27/08/2024
 */
@Getter
public class CommandHandle {
    private final CommandWrapper wrapper;

    public CommandHandle(CommandWrapper wrapper, Method method) {
        this.wrapper = wrapper;
    }

    private void validate() {

    }
}
