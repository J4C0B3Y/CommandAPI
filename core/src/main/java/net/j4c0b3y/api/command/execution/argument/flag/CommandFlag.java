package net.j4c0b3y.api.command.execution.argument.flag;

import lombok.experimental.UtilityClass;
import net.j4c0b3y.api.command.CommandHandler;
import net.j4c0b3y.api.command.exception.execution.UnknownFlagException;
import net.j4c0b3y.api.command.utils.StringUtils;

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

    public String validate(String argument, CommandHandler handler) throws UnknownFlagException {
        int length = argument.length();
        int hyphens = length - getName(argument).length();

        if (hyphens == 1 && length != 2) {
            throw new UnknownFlagException(handler.getLocale().getFlagDoubleHyphen(argument));
        }

        if (hyphens == 2 && length < 4) {
            throw new UnknownFlagException(handler.getLocale().getFlagNameTooShort(argument));
        }

        if (hyphens >= 3) {
            throw new UnknownFlagException(handler.getLocale().getFlagNameHyphen(argument));
        }

        return hyphens != 0 ? argument.substring(hyphens) : null;
    }
}
