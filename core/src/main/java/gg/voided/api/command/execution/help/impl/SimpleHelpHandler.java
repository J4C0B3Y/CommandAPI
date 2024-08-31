package gg.voided.api.command.execution.help.impl;

import gg.voided.api.command.actor.Actor;
import gg.voided.api.command.execution.help.HelpHandler;
import gg.voided.api.command.wrapper.CommandHandle;
import gg.voided.api.command.wrapper.CommandWrapper;

import java.util.List;

/**
 * @author J4C0B3Y
 * @version CommandAPI
 * @since 1/09/2024
 */
public class SimpleHelpHandler implements HelpHandler {

    @Override
    public boolean sendHelp(Actor actor, CommandWrapper wrapper, List<String> arguments) {
        return false;
    }

    @Override
    public void sendUsage(Actor actor, CommandHandle handle, String label) {
        actor.sendMessage("&cUsage: /" + handle.getWrapper().getName() + " " + handle.getName() + " " + handle.getUsage());
    }
}
