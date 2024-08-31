package gg.voided.api.command.bukkit.listener;

import com.destroystokyo.paper.event.server.AsyncTabCompleteEvent;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

/**
 * @author J4C0B3Y
 * @version CommandAPI
 * @since 30/08/2024
 */
public class AsyncTabListener implements Listener {

    @EventHandler
    public void onTabComplete(AsyncTabCompleteEvent event) {
        if (!event.isCommand()) return;
        event.getSender().sendMessage("async-tab: " + event.getBuffer());
    }
}
