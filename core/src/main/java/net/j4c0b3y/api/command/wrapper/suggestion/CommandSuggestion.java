package net.j4c0b3y.api.command.wrapper.suggestion;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import net.j4c0b3y.api.command.actor.Actor;

import java.util.List;

/**
 * @author J4C0B3Y
 * @version CommandAPI
 * @since 27/10/2024
 */
@Getter
@RequiredArgsConstructor
public class CommandSuggestion {
    private final Actor actor;
    private final List<String> arguments;
}
