package gg.voided.api.command;

import gg.voided.api.command.annotation.registration.Ignore;
import gg.voided.api.command.annotation.registration.Register;
import gg.voided.api.command.exception.registration.RegistrationException;
import gg.voided.api.command.execution.argument.UnknownFlagAction;
import gg.voided.api.command.execution.help.HelpHandler;
import gg.voided.api.command.wrapper.CommandWrapper;
import gg.voided.api.command.wrapper.parameter.binding.BindingBuilder;
import gg.voided.api.command.wrapper.parameter.binding.BindingHandler;
import gg.voided.api.command.wrapper.parameter.modifier.ModifierHandler;
import lombok.Getter;
import lombok.Setter;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.logging.Logger;

/**
 * @author J4C0B3Y
 * @version CommandAPI
 * @since 26/08/2024
 */
@Getter @Setter
public abstract class CommandHandler {
    private final BindingHandler bindingHandler = new BindingHandler();
    private final ModifierHandler modifierHandler = new ModifierHandler();

    private UnknownFlagAction unknownFlagAction = UnknownFlagAction.ERROR;
    private HelpHandler helpHandler;

    public abstract CommandWrapper wrap(Object wrapper, String name, List<String> aliases);
    public abstract Logger getLogger();

    public <T> BindingBuilder<T> bind(Class<T> type) {
        return new BindingBuilder<>(type, this);
    }

    public void register(Object wrapper, String name, String... aliases) {
        if (wrapper.getClass().isAnnotationPresent(Ignore.class)) return;

        try {
            wrap(wrapper, name, Arrays.asList(aliases)).register();
        } catch (Exception exception) {
            throw new RegistrationException("Failed to register command '" + name + "'", exception);
        }
    }

    public void register(Object wrapper) {
        Class<?> clazz = wrapper.getClass();
        Register annotation = clazz.getAnnotation(Register.class);

        if (annotation == null) {
            throw new RegistrationException("Wrapper '" + clazz.getSimpleName() + "' must be annotated @Register.");
        }

        register(wrapper, annotation.name(), annotation.aliases());
    }

    public void runTask(Runnable task, boolean async) {
        if (!async) {
            task.run();
            return;
        }

        CompletableFuture.runAsync(task);
    }
}
