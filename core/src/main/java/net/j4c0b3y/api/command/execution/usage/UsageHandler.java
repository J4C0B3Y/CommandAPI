package net.j4c0b3y.api.command.execution.usage;

import net.j4c0b3y.api.command.actor.Actor;
import net.j4c0b3y.api.command.wrapper.CommandHandle;
import net.j4c0b3y.api.command.wrapper.CommandWrapper;

import java.util.List;

/**
 * @author J4C0B3Y
 * @version CommandAPI
 * @since 30/08/24
 */
public interface UsageHandler {
    void sendUsage(Actor actor, CommandHandle handle, String label);
    boolean sendHelp(Actor actor, CommandWrapper wrapper, List<String> arguments);
}
