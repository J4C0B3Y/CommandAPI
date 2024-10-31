package net.j4c0b3y.api.command.velocity;

import com.velocitypowered.api.command.CommandManager;
import com.velocitypowered.api.command.CommandMeta;
import com.velocitypowered.api.command.SimpleCommand;
import net.j4c0b3y.api.command.utils.ListUtils;
import net.j4c0b3y.api.command.velocity.actor.VelocityActor;
import net.j4c0b3y.api.command.wrapper.CommandWrapper;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CompletableFuture;

/**
 * @author J4C0B3Y
 * @version CommandAPI
 * @since 30/08/2024
 */
public class VelocityCommandWrapper extends CommandWrapper implements SimpleCommand {
    private final VelocityCommandHandler velocityHandler;

    public VelocityCommandWrapper(Object instance, String name, List<String> aliases, VelocityCommandHandler handler) {
        super(instance, name, aliases, handler);
        this.velocityHandler = handler;
    }

    @Override
    public void register() {
        CommandManager manager = velocityHandler.getProxy().getCommandManager();

        CommandMeta meta = manager.metaBuilder(getName())
            .aliases(getAliases().toArray(new String[0]))
            .build();

        manager.register(meta, this);
    }

    @Override
    public void execute(Invocation invocation) {
        dispatch(new VelocityActor(invocation.source(), velocityHandler), invocation.alias(), Arrays.asList(invocation.arguments()));
    }

    @Override
    public List<String> suggest(Invocation invocation) {
        List<String> arguments = ListUtils.asList(invocation.arguments());

        if (arguments.isEmpty()) {
            arguments.add("");
        }

        return suggest(new VelocityActor(invocation.source(), velocityHandler), arguments);
    }

    @Override
    public CompletableFuture<List<String>> suggestAsync(Invocation invocation) {
        return CompletableFuture.supplyAsync(() -> suggest(invocation));
    }

    @Override
    public boolean hasPermission(Invocation invocation) {
        return !hasPermission() || invocation.source().hasPermission(getPermission());
    }
}
