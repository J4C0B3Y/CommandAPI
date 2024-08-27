package gg.voided.api.command.wrapper.parameter.provider.impl;

import gg.voided.api.command.execution.CommandExecution;
import gg.voided.api.command.execution.argument.CommandArgument;
import gg.voided.api.command.wrapper.parameter.provider.ArgumentProvider;

/**
 * @author J4C0B3Y
 * @version CommandAPI
 * @since 8/27/24
 */
public class StringProvider extends ArgumentProvider<String> {

    @Override
    public String provide(CommandExecution execution, CommandArgument argument) {
        return argument.getValue();
    }
}
