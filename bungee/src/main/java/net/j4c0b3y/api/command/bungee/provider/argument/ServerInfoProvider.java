package net.j4c0b3y.api.command.bungee.provider.argument;

import net.j4c0b3y.api.command.bungee.BungeeCommandHandler;
import net.j4c0b3y.api.command.exception.execution.ExitMessage;
import net.j4c0b3y.api.command.execution.CommandExecution;
import net.j4c0b3y.api.command.execution.argument.CommandArgument;
import net.j4c0b3y.api.command.wrapper.binding.provider.Provider;
import net.j4c0b3y.api.command.wrapper.binding.provider.ProviderType;
import net.j4c0b3y.api.command.wrapper.suggestion.CommandSuggestion;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.config.ServerInfo;

import java.util.ArrayList;
import java.util.List;

/**
 * @author J4C0B3Y
 * @version CommandAPI
 * @since 25/10/2024
 */
public class ServerInfoProvider extends Provider<ServerInfo> {
    private final BungeeCommandHandler handler;
    private final ProxyServer proxy;

    public ServerInfoProvider(BungeeCommandHandler handler) {
        super(ProviderType.ARGUMENT, "server");
        this.handler = handler;
        this.proxy = handler.getPlugin().getProxy();
    }

    @Override
    public ServerInfo provide(CommandExecution execution, CommandArgument argument) {
        ServerInfo server = proxy.getServerInfo(argument.getValue());

        if (server == null) {
            throw new ExitMessage(handler.getLocale().getInvalidServer(argument.getValue()));
        }

        return server;
    }

    @Override
    public List<String> suggest(CommandSuggestion suggestion, CommandArgument argument) {
        List<String> suggestions = new ArrayList<>();

        for (ServerInfo server : proxy.getServers().values()) {
            suggestions.add(server.getName());
        }

        return suggestions;
    }
}
