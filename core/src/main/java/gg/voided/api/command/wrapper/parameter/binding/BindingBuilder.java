package gg.voided.api.command.wrapper.parameter.binding;

import gg.voided.api.command.CommandHandler;
import gg.voided.api.command.annotation.parameter.classifier.Classifier;
import gg.voided.api.command.annotation.parameter.modifier.Modifier;
import gg.voided.api.command.exception.binding.InvalidBindingException;
import gg.voided.api.command.utils.ClassUtils;
import gg.voided.api.command.wrapper.parameter.modifier.ArgumentModifier;
import gg.voided.api.command.wrapper.parameter.provider.Provider;
import gg.voided.api.command.wrapper.parameter.provider.impl.context.InstanceProvider;
import lombok.RequiredArgsConstructor;

import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

/**
 * @author J4C0B3Y
 * @version CommandAPI
 * @since 27/08/2024
 */
@RequiredArgsConstructor
public class BindingBuilder<T> {
    private final Class<T> type;
    private final CommandHandler handler;

    private final List<Class<? extends Annotation>> classifiers = new ArrayList<>();
    private Class<? extends Annotation> modifier;

    public BindingBuilder<T> annotated(Class<? extends Annotation> annotation) {
        boolean classifier = annotation.isAnnotationPresent(Classifier.class);
        boolean modifier = annotation.isAnnotationPresent(Modifier.class);

        if (classifier && modifier) {
            throw new InvalidBindingException("Annotation '" + annotation.getSimpleName() + "' cannot be @Classifier and @Modifier.");
        }

        if (classifier) {
            if (this.modifier != null) {
                throw new InvalidBindingException("Cannot bind both @Classifier and @Modifier.");
            }

            if (classifiers.contains(annotation)) {
                throw new InvalidBindingException("Cannot bind @Classifier '" + annotation.getSimpleName() + "' multiple times.");
            }

            classifiers.add(annotation);
            return this;
        }

        if (modifier) {
            if (!classifiers.isEmpty()) {
                throw new InvalidBindingException("Cannot bind both @Modifier and @Classifier.");
            }

            if (this.modifier != null) {
                throw new InvalidBindingException("Cannot bind multiple @Modifier at once.");
            }

            this.modifier = annotation;
            return this;
        }

        throw new InvalidBindingException("Annotation '" + annotation.getSimpleName() + "' must have @Classifier or @Modifier.");
    }

    public void to(Provider<T> provider) {
        if (modifier != null) {
            throw new InvalidBindingException("@Modifier cannot be bound to a provider.");
        }

        to(type -> handler.getBindingHandler().put(type, new ParameterBinding<>(type, provider, classifiers)));
    }

    public void to(T instance) {
        to(new InstanceProvider<>(instance));
    }

    public void to(ArgumentModifier<T> modifier) {
        if (this.modifier == null) {
            throw new InvalidBindingException("No @Modifier was provided.");
        }

        to(type -> handler.getModifierHandler().put(type, this.modifier, modifier));
    }

    private void to(Consumer<Class<T>> binder) {
        // If the type is a primitive, bind its wrapper class.
        if (type.isPrimitive()) {
            binder.accept(ClassUtils.wrap(type));
        }

        binder.accept(type);
    }
}
