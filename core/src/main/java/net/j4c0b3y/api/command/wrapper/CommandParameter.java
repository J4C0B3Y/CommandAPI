package net.j4c0b3y.api.command.wrapper;

import lombok.Getter;
import net.j4c0b3y.api.command.annotation.command.Requires;
import net.j4c0b3y.api.command.annotation.parameter.*;
import net.j4c0b3y.api.command.annotation.parameter.classifier.Classifier;
import net.j4c0b3y.api.command.annotation.parameter.modifier.Modifier;
import net.j4c0b3y.api.command.exception.registration.InvalidParameterException;
import net.j4c0b3y.api.command.exception.registration.MissingProviderException;
import net.j4c0b3y.api.command.utils.AnnotationUtils;
import net.j4c0b3y.api.command.utils.ListUtils;
import net.j4c0b3y.api.command.wrapper.binding.provider.Provider;

import java.lang.annotation.Annotation;
import java.lang.reflect.Parameter;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * @author J4C0B3Y
 * @version CommandAPI
 * @since 27/08/2024
 */
@Getter
public class CommandParameter {
    private final Class<?> type;

    private final List<Annotation> annotations;
    private final List<Annotation> classifiers;
    private final List<Annotation> modifiers;

    private final String name;
    private final String defaultValue;
    private final boolean optional;

    private final List<String> options;
    private final List<String> flagNames;

    private final Provider<?> provider;
    private final String permission;

    private final boolean last;
    private final boolean array;
    private final boolean combine;

    public CommandParameter(CommandHandle handle, Parameter parameter) {
        this.type = parameter.getType();

        this.annotations = Arrays.asList(parameter.getAnnotations());
        this.classifiers = AnnotationUtils.getSpecial(annotations, Classifier.class);
        this.modifiers = AnnotationUtils.getSpecial(annotations, Modifier.class);

        this.provider = handle.getWrapper().getHandler().getBindingHandler().assign(this);

        if (this.provider == null) {
            throw new MissingProviderException("Parameter '" + parameter.getName() + "' has no valid providers bound for '" + type.getSimpleName() + "'.");
        }

        this.permission = AnnotationUtils.getValue(parameter, Requires.class, Requires::value, null);

        String name = AnnotationUtils.getValue(parameter, Named.class, Named::value, provider.getDefaultName());
        this.name = name != null ? name : parameter.getName();

        this.defaultValue = AnnotationUtils.getValue(parameter, Default.class, Default::value, null);
        this.optional = defaultValue != null || parameter.isAnnotationPresent(Optional.class);

        if (hasPermission() && !optional) {
            throw new InvalidParameterException("Parameter '" + parameter.getName() + "' has @Requires but is not optional.");
        }

        this.options = AnnotationUtils.getValue(parameter, Option.class, option -> ListUtils.map(option.value(), String::toLowerCase), Collections.emptyList());
        this.flagNames = AnnotationUtils.getValue(parameter, Flag.class, flag -> ListUtils.asList(flag.value()), null);

        // Use the parameter name if no flag names are specified.
        if (flagNames != null && flagNames.isEmpty()) {
            flagNames.add(name);
        }

        this.array = type.isArray();
        this.last = !AnnotationUtils.getSpecial(annotations, Last.class).isEmpty() || type.isArray();
        this.combine = parameter.isAnnotationPresent(Text.class) || this.array;

        if (isCombine() && isFlag()) {
            throw new InvalidParameterException("Parameter '" + parameter.getName() + "' cannot be a flag.");
        }
    }

    public boolean isBoolean() {
        return type == boolean.class || type == Boolean.class;
    }

    public boolean isFlag() {
        return flagNames != null && !flagNames.isEmpty();
    }

    public boolean hasPermission() {
        return permission != null;
    }

    public boolean hasOption(String option) {
        return options.contains(option.toLowerCase());
    }

    @SuppressWarnings("unchecked")
    public <T extends Annotation> T getAnnotation(Class<T> type) {
        for (Annotation annotation : annotations) {
            if (annotation.annotationType().equals(type)) {
                return (T) annotation;
            }
        }

        return null;
    }

    public boolean hasAnnotation(Class<? extends Annotation> type) {
        return getAnnotation(type) != null;
    }
}
