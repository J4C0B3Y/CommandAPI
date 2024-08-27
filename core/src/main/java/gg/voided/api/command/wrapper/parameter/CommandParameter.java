package gg.voided.api.command.wrapper.parameter;

import gg.voided.api.command.annotation.AnnotationHandler;
import gg.voided.api.command.annotation.parameter.Default;
import gg.voided.api.command.annotation.parameter.Flag;
import gg.voided.api.command.annotation.parameter.Last;
import gg.voided.api.command.annotation.parameter.Named;
import gg.voided.api.command.annotation.parameter.classifier.Classifier;
import gg.voided.api.command.annotation.parameter.classifier.Text;
import gg.voided.api.command.annotation.parameter.modifier.Modifier;
import gg.voided.api.command.exception.registration.MissingProviderException;
import gg.voided.api.command.wrapper.CommandHandle;
import gg.voided.api.command.wrapper.parameter.provider.Provider;
import lombok.Getter;

import java.lang.annotation.Annotation;
import java.lang.reflect.Parameter;
import java.util.Arrays;
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
    private final String flagName;

    private final Provider<?> provider;

    private final boolean last;
    private final boolean text;

    public CommandParameter(CommandHandle handle, Parameter parameter) {
        this.type = parameter.getType();

        this.annotations = Arrays.asList(parameter.getAnnotations());
        this.classifiers = AnnotationHandler.getSpecial(annotations, Classifier.class);
        this.modifiers = AnnotationHandler.getSpecial(annotations, Modifier.class);

        this.name = AnnotationHandler.getValue(parameter, Named.class, Named::value, parameter.getName());
        this.defaultValue = AnnotationHandler.getValue(parameter, Default.class, Default::value, null);
        this.flagName = AnnotationHandler.getValue(parameter, Flag.class, Flag::value, null);

        this.provider = handle.getWrapper().getHandler().getBindingHandler().assign(this);

        if (this.provider == null) {
            throw new MissingProviderException("Parameter '" + parameter.getName() + "' has no valid providers bound for '" + type.getSimpleName() + "'.");
        }

        this.last = AnnotationHandler.getSpecial(annotations, Last.class) != null;
        this.text = parameter.isAnnotationPresent(Text.class);
    }

    public boolean isOptional() {
        return defaultValue != null;
    }

    public boolean isBoolean() {
        return type == boolean.class || type == Boolean.class;
    }

    public boolean isFlag() {
        return flagName != null;
    }
}
