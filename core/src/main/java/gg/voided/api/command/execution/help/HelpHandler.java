package gg.voided.api.command.execution.help;

import gg.voided.api.command.actor.Actor;

import java.util.List;

/**
 * @author J4C0B3Y
 * @version CommandAPI
 * @since 8/30/24
 */
public interface HelpHandler {
    void send(Actor actor, List<String> arguments);
}
