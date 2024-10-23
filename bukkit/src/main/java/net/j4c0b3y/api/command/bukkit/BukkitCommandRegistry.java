package net.j4c0b3y.api.command.bukkit;

import lombok.Getter;
import net.j4c0b3y.api.command.bukkit.utils.ClassUtils;
import net.j4c0b3y.api.command.exception.registration.RegistrationException;
import net.j4c0b3y.api.command.wrapper.CommandWrapper;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.PluginCommand;
import org.bukkit.command.SimpleCommandMap;
import org.bukkit.permissions.Permission;
import org.bukkit.permissions.PermissionDefault;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;

import java.lang.reflect.Constructor;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * @author J4C0B3Y
 * @version CommandAPI
 * @since 31/08/2024
 */
@Getter
public class BukkitCommandRegistry {
    private final BukkitCommandHandler handler;

    private final SimpleCommandMap commandMap;
    private final Map<String, Command> knownCommands;
    private final Constructor<PluginCommand> pluginCommand;

    private final Map<Command, CommandWrapper> wrappers = new HashMap<>();

    @SuppressWarnings("unchecked")
    public BukkitCommandRegistry(BukkitCommandHandler handler) {
        this.handler = handler;

        try {
            this.commandMap = (SimpleCommandMap) ClassUtils.getField(Bukkit.getPluginManager(), "commandMap");
            this.knownCommands = new HashMap<>((Map<String, Command>) ClassUtils.getField(this.commandMap, "knownCommands"));
        } catch (Exception exception) {
            throw new IllegalStateException("Failed to access command map!", exception);
        }

        try {
            ClassUtils.setField(this.commandMap, "knownCommands", this.knownCommands);
        } catch (Exception exception) {
            throw new IllegalStateException("Failed to patch command map!");
        }

        try {
            this.pluginCommand = ClassUtils.getConstructor(PluginCommand.class, String.class, Plugin.class);
        } catch (Exception exception) {
            throw new IllegalStateException("Failed to access command constructor!", exception);
        }
    }

    public void register(BukkitCommandWrapper wrapper) {
        try {
            Iterator<Command> iterator = knownCommands.values().iterator();

            while (iterator.hasNext()) {
                Command command = iterator.next();
                String name = command.getName().toLowerCase();

                if (!wrapper.getName().equals(name) && !wrapper.getAliases().contains(name)) {
                    continue;
                }

                if (handler.isDebug()) {
                    Plugin plugin = command instanceof PluginCommand ? ((PluginCommand) command).getPlugin() : null;
                    String origin = plugin != null ? plugin.getDescription().getFullName() : "Unknown";

                    handler.getLogger().warning("Overriding command '" + name + "' from '" + origin + "'.");
                }

                iterator.remove();
            }

            commandMap.register(handler.getPlugin().getName(), wrapper.getCommand());
            wrappers.put(wrapper.getCommand(), wrapper);
        } catch (Exception exception) {
            throw new RegistrationException("Failed to register wrapper in command map!", exception);
        }
    }

    public void registerPermission(String name) {
        if (name == null || name.isEmpty()) return;

        PluginManager pluginManager = handler.getPlugin().getServer().getPluginManager();
        if (pluginManager.getPermission(name) != null) return;

        pluginManager.addPermission(new Permission(name,
            "A permission registered by " + handler.getPlugin().getName(),
            PermissionDefault.OP
        ));
    }

    public PluginCommand getPluginCommand(BukkitCommandWrapper wrapper) {
        try {
            return pluginCommand.newInstance(wrapper.getName(), handler.getPlugin());
        } catch (Exception exception) {
            throw new RegistrationException("Failed to instantiate plugin command!", exception);
        }
    }
}
