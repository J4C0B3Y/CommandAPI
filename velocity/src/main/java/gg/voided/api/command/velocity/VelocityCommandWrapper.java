package gg.voided.api.command.velocity;

import com.velocitypowered.api.command.SimpleCommand;
import gg.voided.api.command.wrapper.CommandWrapper;

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
        velocityHandler.getProxy().getCommandManager().register(getName(), this, getAliases().toArray(new String[0]));
    }

    @Override
    public void execute(Invocation invocation) {
        dispatch(new VelocityActor(invocation.source()), Arrays.asList(invocation.arguments()));
    }

    //TODO: Velocity completions

    @Override
    public List<String> suggest(Invocation invocation) {
        return null;
    }

    @Override
    public CompletableFuture<List<String>> suggestAsync(Invocation invocation) {
        return null;
    }

    @Override
    public boolean hasPermission(Invocation invocation) {
        return invocation.source().hasPermission(getPermission());
    }
}
