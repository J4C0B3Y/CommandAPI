package gg.voided.api.command.bukkit;

import gg.voided.api.command.wrapper.CommandWrapper;
import lombok.Getter;
import org.bukkit.command.*;

import java.util.Arrays;
import java.util.List;

/**
 * @author J4C0B3Y
 * @version CommandAPI
 * @since 30/08/2024
 */
@Getter
public class BukkitCommandWrapper extends CommandWrapper implements CommandExecutor, TabCompleter {
    private final BukkitCommandHandler bukkitHandler;
    private final PluginCommand command;

    public BukkitCommandWrapper(Object instance, String name, List<String> aliases, BukkitCommandHandler handler) {
        super(instance, name, aliases, handler);
        this.bukkitHandler = handler;
        this.command = handler.getRegistry().getPluginCommand(this);

        this.command.setExecutor(this);
        this.command.setTabCompleter(this);
        this.command.setPermission(getPermission());
        this.command.setDescription(getDescription());
        this.command.setAliases(getAliases());
    }

    @Override
    public void register() {
        bukkitHandler.getRegistry().register(this);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] arguments) {
        dispatch(new BukkitActor(sender), label, Arrays.asList(arguments));
        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] arguments) {
        return null;
    }
}
