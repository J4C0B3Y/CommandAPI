package gg.voided.api.command.execution.argument.flag;

import gg.voided.api.command.exception.execution.UnknownFlagException;
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

    public String validate(String argument) {
        int length = argument.length();
        int hyphens = length - getName(argument).length();

        // TODO: Messages

        if (hyphens == 1 && length != 2) {
            throw new UnknownFlagException("must have double hyphen");
        }

        if (hyphens == 2 && length < 4) {
            throw new UnknownFlagException("must be more then 1 char");
        }

        if (hyphens >= 3) {
            throw new UnknownFlagException("flag name cannot start with hyphen");
        }

        return hyphens != 0 ? argument.substring(hyphens) : null;
    }
}
