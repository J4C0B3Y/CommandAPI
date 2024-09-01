package gg.voided.api.command.execution.argument.flag;

import gg.voided.api.command.CommandHandler;
import gg.voided.api.command.exception.execution.ExitMessage;
import gg.voided.api.command.utils.StringUtils;
import lombok.experimental.UtilityClass;

/**
 * @author J4C0B3Y
 * @version CommandAPI
 * @since 1/09/2024
 */
@UtilityClass
public class CommandFlag {
    public String getName(String flag) {
        return flag.replaceAll("^-+", "");
    }

    public String getFlag(String name) {
        return StringUtils.repeat("-", Math.min(name.length(), 2)) + name;
    }

    // TODO: insert hyphens in commandparameter constructor
    // TODO: compare
    public boolean isFlag(String argument, CommandHandler handler) {
        boolean error = handler.getInvalidFlagAction().equals(InvalidFlagAction.ERROR);

        int length = argument.length();
        int hyphens = length - getName(argument).length();

        if (hyphens == 1 && length != 2) {
            if (!error) return false;
            throw new ExitMessage("must have single hyphen");
        }

        if (hyphens == 2 && length < 4) {
            if (!error) return false;
            throw new ExitMessage("must be more then 1 char");
        }

        if (hyphens >= 3) {
            if (!error) return false;
            throw new ExitMessage("flag name cannot start with hyphen");
        }

        return hyphens != 0;
    }

    public static void main(String[] args) {

    }
}
