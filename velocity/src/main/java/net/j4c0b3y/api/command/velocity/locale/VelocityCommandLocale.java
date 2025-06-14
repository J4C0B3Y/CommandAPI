package net.j4c0b3y.api.command.velocity.locale;

/**
 * @author J4C0B3Y
 * @version CommandAPI
 * @since 20/10/2024
 */
public class VelocityCommandLocale {

    public String getVelocityOnly() {
        return "&cThis command can only be run through velocity.";
    }

    public String getPlayerOffline(String argument) {
        return "&cA player with name '" + argument + "' is not on the network.";
    }

}
