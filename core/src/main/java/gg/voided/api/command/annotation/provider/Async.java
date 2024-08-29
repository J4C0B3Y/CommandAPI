package gg.voided.api.command.annotation.provider;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author J4C0B3Y
 * @version CommandAPI
 * @since 30/08/2024
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface Async {
}
