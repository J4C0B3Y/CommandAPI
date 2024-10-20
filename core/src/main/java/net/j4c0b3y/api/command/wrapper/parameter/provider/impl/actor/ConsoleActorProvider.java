package net.j4c0b3y.api.command.wrapper.parameter.provider.impl.actor;

import net.j4c0b3y.api.command.actor.ConsoleActor;
import net.j4c0b3y.api.command.exception.execution.ExitMessage;
import net.j4c0b3y.api.command.execution.CommandExecution;
import net.j4c0b3y.api.command.execution.argument.CommandArgument;
import net.j4c0b3y.api.command.wrapper.parameter.provider.Provider;
import net.j4c0b3y.api.command.wrapper.parameter.provider.ProviderType;

/**
 * @author J4C0B3Y
 * @version CommandAPI
 * @since 9/1/24
 */
public class ConsoleActorProvider extends Provider<ConsoleActor> {

    public ConsoleActorProvider() {
        super(ProviderType.CONTEXT);
    }

    @Override
    public ConsoleActor provide(CommandExecution execution, CommandArgument argument) {
        if (!execution.getActor().isConsole()) {
            throw new ExitMessage(execution.getHandler().getLocale().getConsoleOnly());
        }

        return execution.getActor();
    }
}
