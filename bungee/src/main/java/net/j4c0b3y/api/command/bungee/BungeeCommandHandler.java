package net.j4c0b3y.api.command.bungee;

import lombok.Getter;
import lombok.Setter;
import net.j4c0b3y.api.command.CommandHandler;
import net.j4c0b3y.api.command.bungee.locale.BungeeCommandLocale;
import net.j4c0b3y.api.command.wrapper.CommandWrapper;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.plugin.Plugin;

import java.util.List;
import java.util.logging.Logger;

/**
 * @author J4C0B3Y
 * @version CommandAPI
 * @since 27/08/2024
 */
@Getter @Setter
public class BungeeCommandHandler extends CommandHandler {
    private final Plugin plugin;

    private BungeeCommandLocale bungeeLocale = new BungeeCommandLocale();

    public BungeeCommandHandler(Plugin plugin) {
        this.plugin = plugin;

        setTranslator(text -> ChatColor.translateAlternateColorCodes('&', text));
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
