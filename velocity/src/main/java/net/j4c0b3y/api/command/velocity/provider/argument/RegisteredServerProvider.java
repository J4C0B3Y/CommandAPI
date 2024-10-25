package net.j4c0b3y.api.command.velocity.provider.argument;

import com.velocitypowered.api.proxy.server.RegisteredServer;
import net.j4c0b3y.api.command.actor.Actor;
import net.j4c0b3y.api.command.exception.execution.ExitMessage;
import net.j4c0b3y.api.command.execution.CommandExecution;
import net.j4c0b3y.api.command.execution.argument.CommandArgument;
import net.j4c0b3y.api.command.velocity.VelocityCommandHandler;
import net.j4c0b3y.api.command.wrapper.parameter.provider.Provider;
import net.j4c0b3y.api.command.wrapper.parameter.provider.ProviderType;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * @author J4C0B3Y
 * @version CommandAPI
 * @since 20/10/2024
 */
public class RegisteredServerProvider extends Provider<RegisteredServer> {
    private final VelocityCommandHandler handler;

    public RegisteredServerProvider(VelocityCommandHandler handler) {
        super(ProviderType.ARGUMENT, "server");
        this.handler = handler;
    }

    @Override
    public RegisteredServer provide(CommandExecution execution, CommandArgument argument) {
        Optional<RegisteredServer> server = handler.getProxy().getServer(argument.getValue());

        if (server.isEmpty()) {
            throw new ExitMessage(handler.getLocale().getInvalidServer(argument.getValue()));
        }

        return server.get();
    }

    @Override
    public List<String> suggest(Actor actor, CommandArgument argument) {
        List<String> suggestions = new ArrayList<>();

        for (RegisteredServer server : handler.getProxy().getAllServers()) {
            suggestions.add(server.getServerInfo().getName());
        }

        return suggestions;
    }
}
