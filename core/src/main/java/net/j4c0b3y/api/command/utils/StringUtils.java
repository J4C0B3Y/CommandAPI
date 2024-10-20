package net.j4c0b3y.api.command.utils;

import lombok.experimental.UtilityClass;

import java.util.Collections;

/**
 * @author J4C0B3Y
 * @version CommandAPI
 * @since 30/08/24
 */
@UtilityClass
public class StringUtils {
    public String repeat(String content, int amount) {
        return String.join("", Collections.nCopies(amount, content));
    }

    public boolean startsWithIgnoreCase(String content, String prefix) {
        return content.toLowerCase().startsWith(prefix.toLowerCase());
    }
}