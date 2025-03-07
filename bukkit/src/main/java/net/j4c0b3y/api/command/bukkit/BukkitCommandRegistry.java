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
import java.util.*;

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
            this.commandMap = (SimpleCommandMap) ClassUtils.getField(Bukkit.getServer(), "commandMap");
            this.knownCommands = (Map<String, Command>) ClassUtils.getField(this.commandMap, SimpleCommandMap.class, "knownCommands");
        } catch (Exception exception) {
            throw new IllegalStateException("Failed to access command map!", exception);
        }

        try {
            this.pluginCommand = ClassUtils.getConstructor(PluginCommand.class, String.class, Plugin.class);
        } catch (Exception exception) {
            throw new IllegalStateException("Failed to access command constructor!", exception);
        }
    }

    public void register(BukkitCommandWrapper wrapper) {
        try {
            List<String> labels = new ArrayList<>();
            labels.add(wrapper.getName());
            labels.addAll(wrapper.getAliases());

            for (String label : labels) {
                Command command = knownCommands.get(label);
                if (command == null) continue;

                if (command instanceof PluginCommand) {
                    Plugin plugin = ((PluginCommand) command).getPlugin();
                    if (plugin == handler.getPlugin()) continue;

                    if (handler.isDebug()) {
                        String origin = plugin.getDescription().getName();
                        handler.getLogger().warning("Overriding command '" + label + "' from '" + origin + "'.");
                    }
                }

                knownCommands.remove(label);
            }

            commandMap.register(handler.getPlugin().getName(), wrapper.getCommand());
            wrappers.put(wrapper.getCommand(), wrapper);
        } catch (Exception exception) {
            throw new RegistrationException("Failed to register wrapper in command map!", exception);
        }
    }

    public void registerPermission(String permission, String description) {
        if (permission == null) return;

        PluginManager pluginManager = handler.getPlugin().getServer().getPluginManager();
        if (pluginManager.getPermission(permission) != null) return;

        pluginManager.addPermission(new Permission(permission, description, PermissionDefault.OP));
    }

    public void registerPermission(String permission) {
        registerPermission(permission, "A permission registered by " + handler.getPlugin().getName());
    }

    public PluginCommand getPluginCommand(BukkitCommandWrapper wrapper) {
        try {
            return pluginCommand.newInstance(wrapper.getName(), handler.getPlugin());
        } catch (Exception exception) {
            throw new RegistrationException("Failed to instantiate plugin command!", exception);
        }
    }
}
