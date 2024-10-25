package net.j4c0b3y.api.command.wrapper.binding.provider.impl.argument;

import net.j4c0b3y.api.command.exception.execution.ExitMessage;
import net.j4c0b3y.api.command.execution.CommandExecution;
import net.j4c0b3y.api.command.execution.argument.CommandArgument;
import net.j4c0b3y.api.command.wrapper.binding.provider.Provider;
import net.j4c0b3y.api.command.wrapper.binding.provider.ProviderType;

import java.util.UUID;

/**
 * @author J4C0B3Y
 * @version CommandAPI
 * @since 25/10/2024
 */
public class UUIDProvider extends Provider<UUID> {

    public UUIDProvider() {
        super(ProviderType.ARGUMENT, "uuid");
    }

    @Override
    public UUID provide(CommandExecution execution, CommandArgument argument) {
        try {
            return UUID.fromString(argument.getValue());
        } catch (IllegalArgumentException exception) {
            throw new ExitMessage(execution.getHandler().getLocale()
                .getInvalidType("uuid", argument.getValue())
            );
        }
    }
}
