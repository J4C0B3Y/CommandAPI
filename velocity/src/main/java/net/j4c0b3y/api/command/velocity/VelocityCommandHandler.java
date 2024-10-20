package net.j4c0b3y.api.command.velocity;

import com.velocitypowered.api.command.CommandSource;
import com.velocitypowered.api.proxy.Player;
import com.velocitypowered.api.proxy.ProxyServer;
import com.velocitypowered.api.proxy.server.RegisteredServer;
import lombok.Getter;
import lombok.Setter;
import net.j4c0b3y.api.command.CommandHandler;
import net.j4c0b3y.api.command.annotation.parameter.classifier.Sender;
import net.j4c0b3y.api.command.velocity.actor.VelocityActor;
import net.j4c0b3y.api.command.velocity.locale.VelocityCommandLocale;
import net.j4c0b3y.api.command.velocity.provider.CommandSourceProvider;
import net.j4c0b3y.api.command.velocity.provider.PlayerSenderProvider;
import net.j4c0b3y.api.command.velocity.provider.RegisteredServerProvider;
import net.j4c0b3y.api.command.velocity.provider.VelocityActorProvider;
import net.j4c0b3y.api.command.wrapper.CommandWrapper;

import java.util.List;
import java.util.logging.Logger;

/**
 * @author J4C0B3Y
 * @version CommandAPI
 * @since 27/08/2024
 */
@Getter @Setter
public class VelocityCommandHandler extends CommandHandler {
    private final Object plugin;
    private final ProxyServer proxy;
    private final Logger logger;

    private VelocityCommandLocale velocityLocale = new VelocityCommandLocale();

    public VelocityCommandHandler(Object plugin, ProxyServer proxy, Logger logger) {
        this.plugin = plugin;
        this.proxy = proxy;
        this.logger = logger;
    }

    @Override
    public CommandWrapper wrap(Object wrapper, String name, List<String> aliases) {
        return new VelocityCommandWrapper(wrapper, name, aliases, this);
    }

    @Override
    public void initialize() {
        super.initialize();

        bind(RegisteredServer.class).to(new RegisteredServerProvider(this));

        VelocityActorProvider velocityActorProvider = new VelocityActorProvider(this);

        bind(VelocityActor.class).annotated(Sender.class).to(velocityActorProvider);
        bind(CommandSource.class).annotated(Sender.class).to(new CommandSourceProvider(velocityActorProvider));
        bind(Player.class).annotated(Sender.class).to(new PlayerSenderProvider(velocityActorProvider));
    }
}
