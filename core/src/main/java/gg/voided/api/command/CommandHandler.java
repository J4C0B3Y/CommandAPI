package gg.voided.api.command;

import gg.voided.api.command.annotation.registration.Disabled;
import gg.voided.api.command.execution.argument.UnknownFlagAction;
import gg.voided.api.command.wrapper.parameter.binding.BindingBuilder;
import gg.voided.api.command.wrapper.parameter.binding.BindingHandler;
import gg.voided.api.command.wrapper.parameter.modifier.ModifierHandler;
import lombok.Getter;
import lombok.Setter;

/**
 * @author J4C0B3Y
 * @version CommandAPI
 * @since 26/08/2024
 */
@Getter
public class CommandHandler {
    private final BindingHandler bindingHandler = new BindingHandler();
    private final ModifierHandler modifierHandler = new ModifierHandler();

    @Setter private UnknownFlagAction unknownFlagAction;

    public <T> BindingBuilder<T> bind(Class<T> type) {
        return new BindingBuilder<>(type, this);
    }

    public void register(Object wrapper, String name, String... aliases) {
        if (wrapper.getClass().isAnnotationPresent(Disabled.class)) return;
    }
}
