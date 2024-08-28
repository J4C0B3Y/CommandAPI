package gg.voided.api.command;

import gg.voided.api.command.annotation.command.Command;
import gg.voided.api.command.wrapper.parameter.binding.BindingBuilder;
import gg.voided.api.command.wrapper.parameter.binding.BindingHandler;
import gg.voided.api.command.wrapper.parameter.modifier.ModifierHandler;
import lombok.Getter;

/**
 * @author J4C0B3Y
 * @version CommandAPI
 * @since 26/08/2024
 */
@Getter
public class CommandHandler {
    private final BindingHandler bindingHandler = new BindingHandler();
    private final ModifierHandler modifierHandler = new ModifierHandler();

    public <T> BindingBuilder<T> bind(Class<T> type) {
        return new BindingBuilder<>(type, this);
    }

    @Command(name = "cock", description = "Noonga", aliases = {"test", "test"})
    private void bindDefaults() {
        // TODO: Bind Defaults
    }
}
