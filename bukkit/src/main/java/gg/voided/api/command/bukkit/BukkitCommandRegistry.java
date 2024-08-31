package gg.voided.api.command.bukkit;

import gg.voided.api.command.bukkit.utils.ClassUtils;
import gg.voided.api.command.exception.registration.RegistrationException;
import lombok.Getter;
import org.bukkit.command.Command;
import org.bukkit.command.PluginCommand;
import org.bukkit.command.SimpleCommandMap;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import java.lang.reflect.Constructor;
import java.util.Map;

/**
 * @author J4C0B3Y
 * @version CommandAPI
 * @since 31/08/2024
 */
@Getter
public class BukkitCommandRegistry {
    private final JavaPlugin plugin;

    private final SimpleCommandMap commandMap;
    private final Map<String, Command> knownCommands;
    private final Constructor<PluginCommand> pluginCommand;

    @SuppressWarnings("unchecked")
    public BukkitCommandRegistry(JavaPlugin plugin) {
        this.plugin = plugin;

        try {
            this.commandMap = (SimpleCommandMap) ClassUtils.getField(plugin.getServer().getPluginManager(), "commandMap");
            this.knownCommands = (Map<String, Command>) ClassUtils.getField(this.commandMap, "knownCommands");
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
            for (Command command : knownCommands.values()) {
                String name = command.getName().toLowerCase();

                if (!wrapper.getName().equals(name) && !wrapper.getAliases().contains(name)) {
                    continue;
                }

                command.unregister(commandMap);
                knownCommands.remove(command.getName());
            }

            commandMap.register(plugin.getName(), wrapper.getCommand());
        } catch (Exception exception) {
            throw new RegistrationException("Failed to register wrapper in command map!", exception);
        }
    }

    public PluginCommand getPluginCommand(BukkitCommandWrapper wrapper) {
        try {
            return pluginCommand.newInstance(wrapper.getName(), plugin);
        } catch (Exception exception) {
            throw new RegistrationException("Failed to instantiate plugin command!", exception);
        }
    }
}
