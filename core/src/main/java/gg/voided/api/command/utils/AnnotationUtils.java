package gg.voided.api.command.utils;

import lombok.experimental.UtilityClass;

import java.lang.annotation.Annotation;
import java.lang.reflect.AnnotatedElement;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

/**
 * @author J4C0B3Y
 * @version CommandAPI
 * @since 8/28/24
 */
@UtilityClass
public class AnnotationUtils {
    public <T extends Annotation, R> R getValue(AnnotatedElement element, Class<T> type, Function<T, R> mapper, R fallback) {
        T annotation = element.getAnnotation(type);

        if (annotation == null) {
            return fallback;
        }

        return mapper.apply(annotation);
    }

    public List<Annotation> getSpecial(List<Annotation> annotations, Class<? extends Annotation> type) {
        List<Annotation> special = new ArrayList<>();

        for (Annotation annotation : annotations) {
            if (annotation.annotationType().isAnnotationPresent(type)) {
                special.add(annotation);
            }
        }

        return special;
    }
}
