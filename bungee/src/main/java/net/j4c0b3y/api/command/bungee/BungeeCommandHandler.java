package net.j4c0b3y.api.command.bungee;

import lombok.Getter;
import lombok.Setter;
import net.j4c0b3y.api.command.CommandHandler;
import net.j4c0b3y.api.command.annotation.parameter.classifier.Sender;
import net.j4c0b3y.api.command.bungee.actor.BungeeActor;
import net.j4c0b3y.api.command.bungee.annotation.ConsoleSender;
import net.j4c0b3y.api.command.bungee.locale.BungeeCommandLocale;
import net.j4c0b3y.api.command.bungee.provider.actor.BungeeActorProvider;
import net.j4c0b3y.api.command.bungee.provider.actor.CommandSenderProvider;
import net.j4c0b3y.api.command.bungee.provider.actor.ConsoleCommandSenderProvider;
import net.j4c0b3y.api.command.bungee.provider.actor.ProxiedPlayerSenderProvider;
import net.j4c0b3y.api.command.bungee.provider.argument.ServerInfoProvider;
import net.j4c0b3y.api.command.wrapper.CommandWrapper;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.config.ServerInfo;
import net.md_5.bungee.api.connection.ProxiedPlayer;
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
    }

    @Override
    public CommandWrapper wrap(Object wrapper, String name, List<String> aliases) {
        return new BungeeCommandWrapper(wrapper, name, aliases, this);
    }

    @Override
    public Logger getLogger() {
        return plugin.getLogger();
    }

    @Override
    public void bindDefaults() {
        super.bindDefaults();

        BungeeActorProvider actorProvider = new BungeeActorProvider(this);

        bind(BungeeActor.class).annotated(Sender.class).to(actorProvider);
        bind(ProxiedPlayer.class).annotated(Sender.class).to(new ProxiedPlayerSenderProvider(actorProvider));
        bind(CommandSender.class).annotated(Sender.class).to(new CommandSenderProvider(actorProvider));
        bind(CommandSender.class).annotated(ConsoleSender.class).to(new ConsoleCommandSenderProvider(actorProvider));

        bind(ServerInfo.class).to(new ServerInfoProvider(this));
    }
}
