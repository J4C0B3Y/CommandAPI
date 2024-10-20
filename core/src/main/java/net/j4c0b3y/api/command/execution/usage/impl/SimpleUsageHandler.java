package net.j4c0b3y.api.command.execution.usage.impl;

import net.j4c0b3y.api.command.actor.Actor;
import net.j4c0b3y.api.command.execution.usage.UsageHandler;
import net.j4c0b3y.api.command.wrapper.CommandHandle;
import net.j4c0b3y.api.command.wrapper.CommandWrapper;

import java.util.List;

/**
 * @author J4C0B3Y
 * @version CommandAPI
 * @since 1/09/2024
 */
public class SimpleUsageHandler implements UsageHandler {

    @Override
    public void sendUsage(Actor actor, CommandHandle handle, String label) {
        String space = !handle.getName().isEmpty() ? " " : "";
        actor.sendMessage("&7Usage: /" + label + space + handle.getName() + " " + handle.getUsage());
    }

    @Override
    public boolean sendHelp(Actor actor, CommandWrapper wrapper, List<String> arguments) {
        return false;
    }
}
