package net.j4c0b3y.api.command.actor;

import java.util.UUID;

/**
 * @author J4C0B3Y
 * @version CommandAPI
 * @since 28/08/2024
 */
public interface PlayerActor {
    boolean isPlayer();
    UUID getUniqueId();
}
