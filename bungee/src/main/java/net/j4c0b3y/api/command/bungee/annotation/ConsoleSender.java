package net.j4c0b3y.api.command.bungee.annotation;

import net.j4c0b3y.api.command.annotation.parameter.classifier.Classifier;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author J4C0B3Y
 * @version CommandAPI
 * @since 25/10/2024
 */
@Classifier
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.PARAMETER)
public @interface ConsoleSender {
}
