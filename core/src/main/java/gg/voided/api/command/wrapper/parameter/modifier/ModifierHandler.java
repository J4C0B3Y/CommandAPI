package gg.voided.api.command.wrapper.parameter.modifier;

import gg.voided.api.command.execution.CommandExecution;
import gg.voided.api.command.wrapper.parameter.CommandParameter;

import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author J4C0B3Y
 * @version CommandAPI
 * @since 27/08/2024
 */
public class ModifierHandler {
    private final Map<Class<? extends Annotation>, Map<Class<?>, List<ArgumentModifier<?>>>> modifiers = new HashMap<>();

    public <T> void put(Class<T> type, Class<? extends Annotation> annotation, ArgumentModifier<T> modifier) {
        modifiers.computeIfAbsent(annotation, key -> new HashMap<>()).computeIfAbsent(type, key -> new ArrayList<>()).add(modifier);
    }

    @SuppressWarnings("unchecked")
    public <T> T modify(T value, CommandExecution execution, CommandParameter parameter) {
        for (Annotation annotation : parameter.getModifiers()) {
            Map<Class<?>, List<ArgumentModifier<?>>> map = this.modifiers.get(annotation.getClass());
            if (map == null) continue;

            List<ArgumentModifier<?>> modifiers = map.get(value.getClass());
            if (modifiers == null) continue;

            for (ArgumentModifier<?> modifier : modifiers) {
                value = ((ArgumentModifier<T>) modifier).modify(value, execution, parameter);
            }
        }

        return value;
    }
}
