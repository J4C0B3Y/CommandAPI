package gg.voided.api.command.utils;

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
}
