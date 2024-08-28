package gg.voided.api.command.utils;

import lombok.experimental.UtilityClass;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.Function;

/**
 * @author J4C0B3Y
 * @version CommandAPI
 * @since 8/28/24
 */
@UtilityClass
public class ListUtils {
    public <T, R> List<R> map(List<T> list, Function<T, R> mapper) {
        List<R> mapped = new ArrayList<>();

        for (T item : list) {
            mapped.add(mapper.apply(item));
        }

        return mapped;
    }

    public <T> List<T> reversed(List<T> list) {
        List<T> reversed = new ArrayList<>(list);
        Collections.reverse(reversed);
        return reversed;
    }
}
