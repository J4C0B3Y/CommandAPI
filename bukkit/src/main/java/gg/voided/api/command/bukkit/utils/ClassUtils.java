package gg.voided.api.command.bukkit.utils;

import lombok.experimental.UtilityClass;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;

/**
 * @author J4C0B3Y
 * @version CommandAPI
 * @since 30/08/2024
 */
@UtilityClass
public class ClassUtils {
    public void ifPresent(String className, Runnable callback) {
        try {
            Class.forName(className);
            callback.run();
        } catch (ClassNotFoundException exception) {
            // ignored
        }
    }

    public Object getField(Object object, String name) throws ReflectiveOperationException {
        Field field = object.getClass().getDeclaredField(name);
        field.setAccessible(true);
        return field.get(object);
    }

    public <T> Constructor<T> getConstructor(Class<T> clazz, Class<?> ...parameters) throws ReflectiveOperationException {
        Constructor<T> constructor = clazz.getDeclaredConstructor(parameters);
        constructor.setAccessible(true);
        return constructor;
    }
}
