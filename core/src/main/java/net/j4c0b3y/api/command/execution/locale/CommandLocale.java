package net.j4c0b3y.api.command.execution.locale;

import net.j4c0b3y.api.command.annotation.command.Help;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * @author J4C0B3Y
 * @version CommandAPI
 * @since 1/09/2024
 */
public class CommandLocale {

    public List<String> getNoPermission() {
        return Collections.singletonList(
            "&cYou do not have permission to execute this command!"
        );
    }

    public List<String> getExceptionOccurred() {
        return Arrays.asList(
            "&cAn exception occurred whilst executing this command!",
            "&7Please report this to a server staff member."
        );
    }

    public List<String> getInvalidSubcommand(String label, Help help) {
        List<String> lines = new ArrayList<>();

        lines.add("&cThat subcommand could not be found!");

        if (help != null) {
            lines.add("&7Run '/" + label + " " + help.command() + "' for a list of commands.");
        }

        return lines;
    }

    public String getMissingArgument(String parameter) {
        return "&cMissing argument for '" + parameter + "'.";
    }

    public String getFlagNameHyphen(String argument) {
        return "&cFlag '" + argument + "' cannot start with '-'.";
    }

    public String getFlagNameTooShort(String argument) {
        return "&cFlag '" + argument + "' must be more then one character.";
    }

    public String getFlagDoubleHyphen(String argument) {
        return "&cFlag '" + argument + "' must start with '--'.";
    }

    public String getFlagValueRequired(String argument) {
        return "&cFlag '" + argument + "' requires a value.";
    }

    public String getFlagSpecified(String argument) {
        return "&cFlag '" + argument + "' already specified.";
    }

    public String getUnknownFlag(String argument) {
        return "&cUnknown flag '" + argument + "'.";
    }

    public String getConsoleOnly() {
        return "&cThis command can only be run by console.";
    }

    public String getProxyOnly() {
        return "&cThis command can only be run on a proxy.";
    }

    public String getPlayerOnly() {
        return "&cThis command can only be run by players.";
    }

    public String getInvalidType(String expected, String argument) {
        return "&cType '" + expected + "' expected, '" + argument + "' found.";
    }

    public String getBelowMinimum(String minimum, String argument) {
        return "&cMinimum '" + minimum + "', found '" + argument + "'";
    }

    public String getAboveMaximum(String maximum, String argument) {
        return "&cMaximum '" + maximum + "', found '" + argument + "'";
    }

    public String getInvalidServer(String argument) {
        return "&cA server with name '" + argument + "' doesn't exist.";
    }

    public String getInvalidEnum(String argument, List<String> valid) {
        return "&cNo value found for '" + argument + "', " +
            "valid: " + String.join(", ", valid) + ".";
    }

    public String getMapEntryNotFound(String argument, String keyName, String valueName) {
        return "&cA" + valueName + " with " + keyName + " '" + argument + "' was not found!";
    }
}
