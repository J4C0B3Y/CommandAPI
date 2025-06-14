package net.j4c0b3y.api.command.velocity.provider.argument;

import com.velocitypowered.api.proxy.Player;
import net.j4c0b3y.api.command.exception.execution.ExitMessage;
import net.j4c0b3y.api.command.execution.CommandExecution;
import net.j4c0b3y.api.command.execution.argument.CommandArgument;
import net.j4c0b3y.api.command.velocity.VelocityCommandHandler;
import net.j4c0b3y.api.command.wrapper.binding.provider.Provider;
import net.j4c0b3y.api.command.wrapper.binding.provider.ProviderType;
import net.j4c0b3y.api.command.wrapper.suggestion.CommandSuggestion;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * @author J4C0B3Y
 * @version CommandAPI
 * @since 15/6/2025
 */
public class PlayerProvider extends Provider<Player> {
    private final VelocityCommandHandler handler;

    public PlayerProvider(VelocityCommandHandler handler) {
        super(ProviderType.ARGUMENT, "player");
        this.handler = handler;
    }

    @Override
    public Player provide(CommandExecution execution, CommandArgument argument) {
        Optional<Player> player = handler.getProxy().getPlayer(argument.getValue());

        if (player.isEmpty()) {
            throw new ExitMessage(handler.getVelocityLocale().getPlayerOffline(argument.getValue()));
        }

        return player.get();
    }

    @Override
    public List<String> suggest(CommandSuggestion suggestion, CommandArgument argument) {
        List<String> suggestions = new ArrayList<>();

        for (Player player : handler.getProxy().getAllPlayers()) {
            suggestions.add(player.getUsername());
        }

        return suggestions;
    }
}
