package net.j4c0b3y.api.command.bukkit.locale;

/**
 * @author J4C0B3Y
 * @version CommandAPI
 * @since 20/10/2024
 */
public class BukkitCommandLocale {

    public String getInvalidWorld(String argument) {
        return "&cA world with name '" + argument + "' doesn't exist.";
    }

    public String getPlayerOffline(String argument) {
        return "&cA player with name '" + argument + "' is not online.";
    }

    public String getBukkitOnly() {
        return "&cThis command can only be run through bukkit.";
    }
}
