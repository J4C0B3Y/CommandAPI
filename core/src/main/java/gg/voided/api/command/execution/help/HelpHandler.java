package gg.voided.api.command.execution.help;

import gg.voided.api.command.actor.Actor;
import gg.voided.api.command.wrapper.CommandHandle;
import gg.voided.api.command.wrapper.CommandWrapper;

import java.util.List;

/**
 * @author J4C0B3Y
 * @version CommandAPI
 * @since 30/08/24
 */
public interface HelpHandler {
    boolean sendHelp(Actor actor, CommandWrapper wrapper, List<String> arguments);
    void sendUsage(Actor actor, CommandHandle handle, String label);
}
