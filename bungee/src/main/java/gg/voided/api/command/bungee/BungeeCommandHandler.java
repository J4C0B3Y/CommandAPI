package gg.voided.api.command.bungee;

import gg.voided.api.command.CommandHandler;
import gg.voided.api.command.wrapper.CommandWrapper;
import lombok.Getter;
import net.md_5.bungee.api.plugin.Plugin;

import java.util.List;
import java.util.logging.Logger;

/**
 * @author J4C0B3Y
 * @version CommandAPI
 * @since 27/08/2024
 */
@Getter
public class BungeeCommandHandler extends CommandHandler {
    private final Plugin plugin;

    public BungeeCommandHandler(Plugin plugin) {
        this.plugin = plugin;
        //TODO: Bind bungee defaults.
    }

    @Override
    public CommandWrapper wrap(Object wrapper, String name, List<String> aliases) {
        return new BungeeCommandWrapper(wrapper, name, aliases, this);
    }

    @Override
    public Logger getLogger() {
        return plugin.getLogger();
    }
}
