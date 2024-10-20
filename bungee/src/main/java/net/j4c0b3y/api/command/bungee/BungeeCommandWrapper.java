package net.j4c0b3y.api.command.bungee;

import net.j4c0b3y.api.command.bungee.actor.BungeeActor;
import net.j4c0b3y.api.command.wrapper.CommandWrapper;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.plugin.Command;
import net.md_5.bungee.api.plugin.TabExecutor;

import java.util.Arrays;
import java.util.List;

/**
 * @author J4C0B3Y
 * @version CommandAPI
 * @since 30/08/2024
 */
public class BungeeCommandWrapper extends CommandWrapper {
    private final BungeeCommandHandler bungeeHandler;
    private final BungeeCommand command;

    public BungeeCommandWrapper(Object instance, String name, List<String> aliases, BungeeCommandHandler handler) {
        super(instance, name, aliases, handler);
        this.bungeeHandler = handler;
        this.command = new BungeeCommand(this);
    }

    @Override
    public void register() {
        ProxyServer.getInstance().getPluginManager().registerCommand(bungeeHandler.getPlugin(), command);
    }

    public static class BungeeCommand extends Command implements TabExecutor {
        private final BungeeCommandWrapper wrapper;

        public BungeeCommand(BungeeCommandWrapper wrapper) {
            super(wrapper.getName(), wrapper.getPermission(), wrapper.getAliases().toArray(new String[0]));
            this.wrapper = wrapper;
        }

        @Override
        public void execute(CommandSender sender, String[] arguments) {
            wrapper.dispatch(new BungeeActor(sender), getName(), Arrays.asList(arguments));
        }

        @Override
        public Iterable<String> onTabComplete(CommandSender sender, String[] arguments) {
            return wrapper.suggest(new BungeeActor(sender), Arrays.asList(arguments));
        }

        @Override
        public boolean hasPermission(CommandSender sender) {
            return sender.hasPermission(wrapper.getPermission());
        }
    }
}
