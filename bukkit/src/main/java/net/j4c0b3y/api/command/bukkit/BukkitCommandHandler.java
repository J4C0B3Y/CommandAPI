package net.j4c0b3y.api.command.bukkit;

import lombok.Getter;
import lombok.Setter;
import net.j4c0b3y.api.command.CommandHandler;
import net.j4c0b3y.api.command.annotation.parameter.classifier.Sender;
import net.j4c0b3y.api.command.bukkit.actor.BukkitActor;
import net.j4c0b3y.api.command.bukkit.listener.AsyncTabListener;
import net.j4c0b3y.api.command.bukkit.locale.BukkitCommandLocale;
import net.j4c0b3y.api.command.bukkit.provider.*;
import net.j4c0b3y.api.command.bukkit.utils.ClassUtils;
import net.j4c0b3y.api.command.wrapper.CommandWrapper;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.World;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.logging.Logger;

/**
 * @author J4C0B3Y
 * @version CommandAPI
 * @since 27/08/2024
 */
@Getter @Setter
public class BukkitCommandHandler extends CommandHandler {
    private final JavaPlugin plugin;
    private final BukkitCommandRegistry registry;

    private BukkitCommandLocale bukkitLocale = new BukkitCommandLocale();

    public BukkitCommandHandler(JavaPlugin plugin) {
        this.plugin = plugin;
        this.registry = new BukkitCommandRegistry(this);

        setDebug(true);

        ClassUtils.ifPresent("com.destroystokyo.paper.event.server.AsyncTabCompleteEvent", () -> {
            plugin.getServer().getPluginManager().registerEvents(new AsyncTabListener(this), plugin);

            if (isDebug()) {
                plugin.getLogger().info("Enabled async tab completion support.");
            }
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

    @Override
    public void initialize() {
        super.initialize();

        bind(World.class).to(new WorldProvider(this));

        bind(Player.class).to(new PlayerProvider(this));
        bind(OfflinePlayer.class).to(new OfflinePlayerProvider());

        BukkitActorProvider bukkitActorProvider = new BukkitActorProvider(this);

        bind(BukkitActor.class).annotated(Sender.class).to(bukkitActorProvider);
        bind(Player.class).annotated(Sender.class).to(new PlayerSenderProvider(bukkitActorProvider));
        bind(CommandSender.class).annotated(Sender.class).to(new CommandSenderProvider(bukkitActorProvider));
        bind(ConsoleCommandSender.class).annotated(Sender.class).to(new ConsoleCommandSenderProvider(bukkitActorProvider));
    }
}
