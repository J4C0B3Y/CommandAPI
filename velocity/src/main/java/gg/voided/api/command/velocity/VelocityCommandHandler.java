package gg.voided.api.command.velocity;

import com.velocitypowered.api.proxy.ProxyServer;
import gg.voided.api.command.CommandHandler;
import gg.voided.api.command.wrapper.CommandWrapper;
import lombok.Getter;

import java.util.List;
import java.util.logging.Logger;

/**
 * @author J4C0B3Y
 * @version CommandAPI
 * @since 27/08/2024
 */
@Getter
public class VelocityCommandHandler extends CommandHandler {
    private final Object plugin;
    private final ProxyServer proxy;
    private final Logger logger;

    public VelocityCommandHandler(Object plugin, Logger logger, ProxyServer proxy) {
        this.plugin = plugin;
        this.proxy = proxy;
        this.logger = logger;
    }

    @Override
    public CommandWrapper wrap(Object wrapper, String name, List<String> aliases) {
        return new VelocityCommandWrapper(wrapper, name, aliases, this);
    }

}
