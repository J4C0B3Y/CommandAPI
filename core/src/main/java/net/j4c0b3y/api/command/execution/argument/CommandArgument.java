package net.j4c0b3y.api.command.execution.argument;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import net.j4c0b3y.api.command.wrapper.parameter.CommandParameter;

/**
 * @author J4C0B3Y
 * @version CommandAPI
 * @since 27/08/2024
 */
@Getter
@RequiredArgsConstructor
public class CommandArgument {
    private final String value;
    private final CommandParameter parameter;
}
