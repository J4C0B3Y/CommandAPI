package net.j4c0b3y.api.command.wrapper.binding;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import net.j4c0b3y.api.command.wrapper.CommandParameter;
import net.j4c0b3y.api.command.wrapper.binding.provider.Provider;

import java.lang.annotation.Annotation;
import java.util.List;

/**
 * @author J4C0B3Y
 * @version CommandAPI
 * @since 27/08/2024
 */
@Getter
@RequiredArgsConstructor
public class ParameterBinding<T> {
    private final Class<T> type;
    private final Provider<T> provider;
    private final List<Class<? extends Annotation>> classifiers;

    public boolean provides(CommandParameter parameter) {
        if (parameter.getClassifiers().size() != classifiers.size()) {
            return false;
        }

        for (Annotation classifier : parameter.getClassifiers()) {
            if (!classifiers.contains(classifier.annotationType())) {
                return false;
            }
        }

        return true;
    }
}
