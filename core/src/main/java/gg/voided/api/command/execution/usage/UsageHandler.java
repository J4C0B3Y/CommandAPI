package gg.voided.api.command.execution.usage;

import gg.voided.api.command.actor.Actor;
import gg.voided.api.command.wrapper.CommandHandle;
import gg.voided.api.command.wrapper.CommandWrapper;

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
