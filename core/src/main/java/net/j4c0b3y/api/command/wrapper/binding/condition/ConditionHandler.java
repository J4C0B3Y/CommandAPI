package net.j4c0b3y.api.command.wrapper.binding.condition;

import net.j4c0b3y.api.command.actor.Actor;
import net.j4c0b3y.api.command.annotation.command.condition.Condition;
import net.j4c0b3y.api.command.exception.execution.MissingValidatorException;

import java.lang.annotation.Annotation;
import java.util.HashMap;
import java.util.Map;

/**
 * @author J4C0B3Y
 * @version CommandAPI
 * @since 25/10/2024
 */
public class ConditionHandler {
    private final Map<Class<?>, ExecuteCondition<?>> conditions = new HashMap<>();

    public <T> void put(Class<T> annotation, ExecuteCondition<T> condition) {
        if (!annotation.isAnnotation() || !annotation.isAnnotationPresent(Condition.class)) {
            throw new IllegalArgumentException(annotation.getSimpleName() + " must be an annotation marked @Condition.");
        }

        conditions.put(annotation, condition);
    }

    @SuppressWarnings("unchecked")
    public <T extends Annotation> boolean validate(T annotation, Actor actor) {
        Class<?> type = annotation.annotationType();

        if (!type.isAnnotationPresent(Condition.class)) {
            throw new IllegalArgumentException("Cannot validate using a non @Condition annotation.");
        }

        if (!conditions.containsKey(annotation.annotationType())) {
            throw new MissingValidatorException("No validator bound for condition '@" + type.getSimpleName() + "'.");
        }

        ExecuteCondition<T> condition = (ExecuteCondition<T>) conditions.get(type);
        return condition.validate(actor, annotation);
    }

    public void clear() {
        conditions.clear();
    }
}
