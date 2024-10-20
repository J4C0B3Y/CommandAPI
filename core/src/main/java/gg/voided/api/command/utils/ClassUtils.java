package gg.voided.api.command.utils;

import lombok.experimental.UtilityClass;

import java.lang.invoke.MethodType;

/**
 * @author J4C0B3Y
 * @version CommandAPI
 * @since 17/10/2024
 */
@UtilityClass
public class ClassUtils {

    @SuppressWarnings("unchecked")
    public <T> Class<T> wrap(Class<T> type) {
        return (Class<T>) MethodType.methodType(type).wrap().returnType();
    }
}
