package gg.voided.api.command.bukkit;

import gg.voided.api.command.CommandHandler;
import gg.voided.api.command.bukkit.listener.AsyncTabListener;
import gg.voided.api.command.bukkit.utils.ClassUtils;
import gg.voided.api.command.wrapper.CommandWrapper;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.logging.Logger;

/**
 * @author J4C0B3Y
 * @version CommandAPI
 * @since 27/08/2024
 */
@Getter
public class BukkitCommandHandler extends CommandHandler {
    private final JavaPlugin plugin;
    private final BukkitCommandRegistry registry;

    public BukkitCommandHandler(JavaPlugin plugin) {
        this.plugin = plugin;
        this.registry = new BukkitCommandRegistry(plugin);

        ClassUtils.ifPresent("com.destroystokyo.paper.event.server.AsyncTabCompleteEvent", () -> {
            plugin.getServer().getPluginManager().registerEvents(new AsyncTabListener(), plugin);
            plugin.getLogger().info("Enabled async tab completion support!");
        });
    }

    @Override
    public CommandWrapper wrap(Object wrapper, String name, List<String> aliases) {
        return new BukkitCommandWrapper(wrapper, name, aliases, this);
    }

    @Override
    public Logger getLogger() {
        return plugin.getLogger();
    }

    @Override
    public void runTask(Runnable task, boolean async) {
        if (async == !Bukkit.isPrimaryThread()) {
            task.run();
            return;
        }

        if (async) {
            CompletableFuture.runAsync(task);
            return;
        }

        Bukkit.getScheduler().runTask(plugin, task);
    }
}
