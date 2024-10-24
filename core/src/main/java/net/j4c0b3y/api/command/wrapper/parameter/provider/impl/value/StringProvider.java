package net.j4c0b3y.api.command.wrapper.parameter.provider.impl.value;

import net.j4c0b3y.api.command.execution.CommandExecution;
import net.j4c0b3y.api.command.execution.argument.CommandArgument;
import net.j4c0b3y.api.command.wrapper.parameter.provider.Provider;
import net.j4c0b3y.api.command.wrapper.parameter.provider.ProviderType;

/**
 * @author J4C0B3Y
 * @version CommandAPI
 * @since 27/08/24
 */
public class StringProvider extends Provider<String> {

    public StringProvider() {
        super(ProviderType.ARGUMENT, "string");
    }

    @Override
    public String provide(CommandExecution execution, CommandArgument argument) {
        return argument.getValue();
    }
}
