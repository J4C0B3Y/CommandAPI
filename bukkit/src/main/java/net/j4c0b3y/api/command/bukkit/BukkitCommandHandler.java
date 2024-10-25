package net.j4c0b3y.api.command.bukkit;

import lombok.Getter;
import lombok.Setter;
import net.j4c0b3y.api.command.CommandHandler;
import net.j4c0b3y.api.command.annotation.parameter.classifier.Sender;
import net.j4c0b3y.api.command.bukkit.actor.BukkitActor;
import net.j4c0b3y.api.command.bukkit.listener.AsyncTabListener;
import net.j4c0b3y.api.command.bukkit.locale.BukkitCommandLocale;
import net.j4c0b3y.api.command.bukkit.provider.actor.*;
import net.j4c0b3y.api.command.bukkit.provider.argument.EnchantmentProvider;
import net.j4c0b3y.api.command.bukkit.provider.argument.OfflinePlayerProvider;
import net.j4c0b3y.api.command.bukkit.provider.argument.PlayerProvider;
import net.j4c0b3y.api.command.bukkit.provider.argument.WorldProvider;
import net.j4c0b3y.api.command.bukkit.utils.ClassUtils;
import net.j4c0b3y.api.command.wrapper.CommandWrapper;
import net.j4c0b3y.api.command.wrapper.parameter.provider.impl.argument.EnumProvider;
import org.bukkit.*;
import org.bukkit.attribute.Attribute;
import org.bukkit.block.Biome;
import org.bukkit.block.BlockFace;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.potion.PotionType;

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

        setTranslator(text -> ChatColor.translateAlternateColorCodes('&', text));

        Bukkit.getScheduler().runTask(plugin, () ->
            ClassUtils.ifPresent("com.destroystokyo.paper.event.server.AsyncTabCompleteEvent", () -> {
                plugin.getServer().getPluginManager().registerEvents(new AsyncTabListener(this), plugin);

                if (isDebug()) {
                    plugin.getLogger().info("Enabled async tab completion support.");
                }
            })
        );
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
    public void bindDefaults() {
        super.bindDefaults();

        BukkitActorProvider actorProvider = new BukkitActorProvider(this);

        bind(BukkitActor.class).annotated(Sender.class).to(actorProvider);
        bind(Player.class).annotated(Sender.class).to(new PlayerSenderProvider(actorProvider));
        bind(CommandSender.class).annotated(Sender.class).to(new CommandSenderProvider(actorProvider));
        bind(ConsoleCommandSender.class).annotated(Sender.class).to(new ConsoleCommandSenderProvider(actorProvider));

        bind(Player.class).to(new PlayerProvider(this));
        bind(OfflinePlayer.class).to(new OfflinePlayerProvider(this));

        bind(World.class).to(new WorldProvider(this));
        bind(Enchantment.class).to(new EnchantmentProvider());

        bind(EntityEffect.class).to(new EnumProvider<>(EntityEffect.class, "effect"));
        bind(WeatherType.class).to(new EnumProvider<>(WeatherType.class, "weather"));
        bind(EntityType.class).to(new EnumProvider<>(EntityType.class, "entity"));
        bind(PotionType.class).to(new EnumProvider<>(PotionType.class, "potion"));
        bind(TreeSpecies.class).to(new EnumProvider<>(TreeSpecies.class, "tree"));
        bind(GameMode.class).to(new EnumProvider<>(GameMode.class, "gamemode"));
        bind(ChatColor.class).to(new EnumProvider<>(ChatColor.class, "color"));
        bind(BlockFace.class).to(new EnumProvider<>(BlockFace.class, "face"));
        bind(DyeColor.class).to(new EnumProvider<>(DyeColor.class, "color"));
        bind(TreeType.class).to(new EnumProvider<>(TreeType.class, "tree"));
        bind(Art.class).to(new EnumProvider<>(Art.class, "painting"));

        bind(World.Environment.class).to(new EnumProvider<>(World.Environment.class));
        bind(Instrument.class).to(new EnumProvider<>(Instrument.class));
        bind(Difficulty.class).to(new EnumProvider<>(Difficulty.class));
        bind(Attribute.class).to(new EnumProvider<>(Attribute.class));
        bind(WorldType.class).to(new EnumProvider<>(WorldType.class));
        bind(SkullType.class).to(new EnumProvider<>(SkullType.class));
        bind(Material.class).to(new EnumProvider<>(Material.class));
        bind(Particle.class).to(new EnumProvider<>(Particle.class));
        bind(Effect.class).to(new EnumProvider<>(Effect.class));
        bind(Sound.class).to(new EnumProvider<>(Sound.class));
        bind(Biome.class).to(new EnumProvider<>(Biome.class));
    }
}
