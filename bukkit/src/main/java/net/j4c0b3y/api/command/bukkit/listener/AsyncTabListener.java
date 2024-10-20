package net.j4c0b3y.api.command.bukkit.listener;

import com.destroystokyo.paper.event.server.AsyncTabCompleteEvent;
import lombok.RequiredArgsConstructor;
import net.j4c0b3y.api.command.bukkit.BukkitCommandHandler;
import net.j4c0b3y.api.command.bukkit.actor.BukkitActor;
import net.j4c0b3y.api.command.utils.ListUtils;
import net.j4c0b3y.api.command.wrapper.CommandWrapper;
import org.bukkit.command.Command;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import java.util.List;

/**
 * @author J4C0B3Y
 * @version CommandAPI
 * @since 30/08/2024
 */
@RequiredArgsConstructor
public class AsyncTabListener implements Listener {
    private final BukkitCommandHandler handler;

    @EventHandler
    public void onTabComplete(AsyncTabCompleteEvent event) {
        if (!event.isCommand() || !event.getBuffer().contains(" ")) return;

        List<String> arguments = ListUtils.asList(event.getBuffer().split(" ", -1));
        String name = arguments.remove(0);

        if (event.getSender() instanceof Player) {
            name = name.replaceFirst("/", "");
        }

        Command command = handler.getRegistry().getKnownCommands().get(name);
        if (command == null) return;

        CommandWrapper wrapper = handler.getRegistry().getWrappers().get(command);
        if (wrapper == null) return;

        List<String> suggestions = wrapper.suggest(new BukkitActor(event.getSender()), arguments);

        if (suggestions.isEmpty()) {
            return;
        }

        if (suggestions.size() == 1 && suggestions.get(0).isEmpty()) {
            // https://bugs.mojang.com/browse/MC-165562
            suggestions.set(0, " ");
        }

        for (String suggestion : suggestions) {
            event.getCompletions().add(suggestion);
        }

        event.setHandled(true);
    }
}
