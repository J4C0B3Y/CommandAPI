package gg.voided.api.command.execution.argument;

import gg.voided.api.command.wrapper.parameter.CommandParameter;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

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
