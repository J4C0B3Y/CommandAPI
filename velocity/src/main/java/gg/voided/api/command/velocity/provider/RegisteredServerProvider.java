package gg.voided.api.command.velocity.provider;

import com.velocitypowered.api.proxy.server.RegisteredServer;
import gg.voided.api.command.actor.Actor;
import gg.voided.api.command.exception.execution.ExitMessage;
import gg.voided.api.command.execution.CommandExecution;
import gg.voided.api.command.execution.argument.CommandArgument;
import gg.voided.api.command.velocity.VelocityCommandHandler;
import gg.voided.api.command.wrapper.parameter.provider.Provider;
import gg.voided.api.command.wrapper.parameter.provider.ProviderType;

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
        super(ProviderType.ARGUMENT);
        this.handler = handler;
    }

    @Override
    public RegisteredServer provide(CommandExecution execution, CommandArgument argument) {
        Optional<RegisteredServer> server = handler.getProxy().getServer(argument.getValue().toLowerCase());

        if (server.isEmpty()) {
            throw new ExitMessage(handler.getVelocityLocale().getInvalidServer(argument.getValue()));
        }

        return server.get();
    }

    @Override
    public List<String> suggest(Actor actor) {
        List<String> suggestions = new ArrayList<>();

        for (RegisteredServer server : handler.getProxy().getAllServers()) {
            suggestions.add(server.getServerInfo().getName());
        }

        return suggestions;
    }
}
