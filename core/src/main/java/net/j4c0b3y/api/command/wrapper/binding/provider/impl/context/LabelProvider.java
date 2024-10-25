package net.j4c0b3y.api.command.wrapper.binding.provider.impl.context;

import net.j4c0b3y.api.command.execution.CommandExecution;
import net.j4c0b3y.api.command.execution.argument.CommandArgument;
import net.j4c0b3y.api.command.wrapper.binding.provider.ProviderType;
import net.j4c0b3y.api.command.wrapper.binding.provider.Provider;

/**
 * @author J4C0B3Y
 * @version CommandAPI
 * @since 1/09/2024
 */
public class LabelProvider extends Provider<String> {

    public LabelProvider() {
        super(ProviderType.CONTEXT);
    }

    @Override
    public String provide(CommandExecution execution, CommandArgument argument) {
        return execution.getLabel();
    }
}
