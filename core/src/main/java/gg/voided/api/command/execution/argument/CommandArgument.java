package gg.voided.api.command.execution.argument;

import gg.voided.api.command.exception.execution.InvalidArgumentException;
import gg.voided.api.command.utils.StringUtils;
import gg.voided.api.command.wrapper.parameter.CommandParameter;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * @author J4C0B3Y
 * @version CommandAPI
 * @since 27/08/2024
 */
@Getter
@RequiredArgsConstructor
public class CommandArgument {
    private final String value;
    private final CommandParameter parameter;

    public static boolean isFlag(String argument, UnknownFlagAction action) {
        boolean throwError = action.equals(UnknownFlagAction.ERROR);

        switch (argument.length() - getFlagName(argument).length()) {
            case 0: return false;

            case 1: {
                if (argument.length() == 2) return true;
                if (!throwError) return false;
                throw new InvalidArgumentException("Flag '" + argument + "' must have a single '-'.");
            }

            case 2: {
                if (argument.length() > 3) return true;
                if (!throwError) return false;
                throw new InvalidArgumentException("Flag '" + argument + "' must be more then one character.");
            }

            default: {
                if (!throwError) return false;
                throw new InvalidArgumentException("Flag '" + argument + "' cannot start with '-'.");
            }
        }
    }

    public static String getFlagName(String argument) {
        return argument.replaceAll("^-+", "");
    }

    public static String getFlagArgument(String flagName) {
        return StringUtils.repeat("-", Math.min(flagName.length(), 2)) + flagName;
    }
}
