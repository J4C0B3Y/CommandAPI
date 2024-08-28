package gg.voided.api.command.utils;

import lombok.experimental.UtilityClass;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.function.Function;

/**
 * @author J4C0B3Y
 * @version CommandAPI
 * @since 28/08/2024
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

    public <T, R> List<R> map(T[] array, Function<T, R> mapper) {
        return map(Arrays.asList(array), mapper);
    }

    public <T> List<T> reversed(List<T> list) {
        List<T> reversed = new ArrayList<>(list);
        Collections.reverse(reversed);
        return reversed;
    }
}
