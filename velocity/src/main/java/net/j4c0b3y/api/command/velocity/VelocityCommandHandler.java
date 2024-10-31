package net.j4c0b3y.api.command.velocity;

import com.velocitypowered.api.command.CommandSource;
import com.velocitypowered.api.proxy.ConsoleCommandSource;
import com.velocitypowered.api.proxy.Player;
import com.velocitypowered.api.proxy.ProxyServer;
import com.velocitypowered.api.proxy.server.RegisteredServer;
import lombok.Getter;
import lombok.Setter;
import net.j4c0b3y.api.command.CommandHandler;
import net.j4c0b3y.api.command.annotation.parameter.classifier.Sender;
import net.j4c0b3y.api.command.velocity.actor.VelocityActor;
import net.j4c0b3y.api.command.velocity.locale.VelocityCommandLocale;
import net.j4c0b3y.api.command.velocity.provider.actor.CommandSourceProvider;
import net.j4c0b3y.api.command.velocity.provider.actor.ConsoleCommandSourceProvider;
import net.j4c0b3y.api.command.velocity.provider.actor.PlayerSenderProvider;
import net.j4c0b3y.api.command.velocity.provider.actor.VelocityActorProvider;
import net.j4c0b3y.api.command.velocity.provider.argument.RegisteredServerProvider;
import net.j4c0b3y.api.command.wrapper.CommandWrapper;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;

import java.util.List;
import java.util.function.Function;
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
    private Function<String, Component> velocityTranslator = LegacyComponentSerializer.legacyAmpersand()::deserialize;

    public VelocityCommandHandler(Object plugin, ProxyServer proxy, Logger logger) {
        this.plugin = plugin;
        this.proxy = proxy;
        this.logger = logger;

        bindDefaults();
    }

    @Override
    public CommandWrapper wrap(Object wrapper, String name, List<String> aliases) {
        return new VelocityCommandWrapper(wrapper, name, aliases, this);
    }

    @Override
    public void bindDefaults() {
        super.bindDefaults();

        VelocityActorProvider actorProvider = new VelocityActorProvider(this);

        bind(VelocityActor.class).annotated(Sender.class).to(actorProvider);
        bind(Player.class).annotated(Sender.class).to(new PlayerSenderProvider(actorProvider));
        bind(CommandSource.class).annotated(Sender.class).to(new CommandSourceProvider(actorProvider));
        bind(ConsoleCommandSource.class).annotated(Sender.class).to(new ConsoleCommandSourceProvider(actorProvider));

        bind(RegisteredServer.class).to(new RegisteredServerProvider(this));
    }
}
