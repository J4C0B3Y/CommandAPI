package net.j4c0b3y.api.command.execution;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import net.j4c0b3y.api.command.wrapper.parameter.CommandParameter;

/**
 * @author J4C0B3Y
 * @version CommandAPI
 * @since 29/08/2024
 */
@Getter
@RequiredArgsConstructor
public class ProvidedParameter {
    private final CommandParameter parameter;
    private @Setter String argument;

    private boolean provided;
    private Object value;

    public void provide(Object value) {
        this.value = value;
        this.provided = true;
    }
}
