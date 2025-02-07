package net.j4c0b3y.api.command.execution.usage.impl;

import lombok.RequiredArgsConstructor;
import net.j4c0b3y.api.command.actor.Actor;
import net.j4c0b3y.api.command.execution.usage.UsageHandler;
import net.j4c0b3y.api.command.wrapper.CommandHandle;
import net.j4c0b3y.api.command.wrapper.CommandWrapper;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author J4C0B3Y
 * @version CommandAPI
 * @since 1/09/2024
 */
@RequiredArgsConstructor
public class SimpleUsageHandler implements UsageHandler {

    @Override
    public List<String> getUsageMessage(Actor actor, CommandHandle handle, String label) {
        return Collections.singletonList("&7Usage: " + getFullUsage(handle, label));
    }

    @Override
    public List<String> getHelpMessage(Actor actor, CommandWrapper wrapper, String label, List<String> arguments) {
        if (arguments.isEmpty() || !arguments.get(0).equals(wrapper.getHelp().command())) {
            return null;
        }

        List<String> lines = new ArrayList<>();

        lines.add("");
        lines.add("&7&m---&7 Showing help for &f/" + label + "&7. &m---");
        lines.add("");

        for (CommandHandle handle : wrapper.getHandles().values()) {
            if (handle.isHidden() || !actor.hasPermission(handle.getPermission())) continue;

            String description = handle.hasDescription() ? " (" + handle.getDescription() + ")" : "";
            lines.add("&7 » &f" + getFullUsage(handle, label) + "&7" + description);
        }

        lines.add("");
        return lines;
    }

    private String getFullUsage(CommandHandle handle, String label) {
        String labelSpace = !handle.getName().isEmpty() ? " " : "";
        String nameSpace = !handle.getUsage().isEmpty() ? " " : "";

        return "/" + label + labelSpace + handle.getName() + nameSpace + handle.getUsage();
    }
}
