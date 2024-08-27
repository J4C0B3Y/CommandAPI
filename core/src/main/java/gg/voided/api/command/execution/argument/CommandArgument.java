package gg.voided.api.command.execution.argument;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.lang.annotation.Annotation;
import java.util.List;

/**
 * @author J4C0B3Y
 * @version CommandAPI
 * @since 8/27/24
 */
@Getter
@RequiredArgsConstructor
public class CommandArgument {
    private final String value;
    private final List<Annotation> annotations;
}
