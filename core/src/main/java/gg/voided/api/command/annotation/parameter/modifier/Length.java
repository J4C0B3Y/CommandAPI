package gg.voided.api.command.annotation.parameter.modifier;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author J4C0B3Y
 * @version CommandAPI
 * @since 28/08/2024
 */
@Modifier
@Target(ElementType.PARAMETER)
@Retention(RetentionPolicy.RUNTIME)
public @interface Length {
    double min() default 0;
    double max() default Integer.MAX_VALUE;
}
