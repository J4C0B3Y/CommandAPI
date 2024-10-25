package net.j4c0b3y.api.command.bukkit.provider.argument;

import net.j4c0b3y.api.command.actor.Actor;
import net.j4c0b3y.api.command.exception.execution.ExitMessage;
import net.j4c0b3y.api.command.execution.CommandExecution;
import net.j4c0b3y.api.command.execution.argument.CommandArgument;
import net.j4c0b3y.api.command.wrapper.parameter.provider.Provider;
import net.j4c0b3y.api.command.wrapper.parameter.provider.ProviderType;
import org.bukkit.enchantments.Enchantment;

import java.util.ArrayList;
import java.util.List;

/**
 * @author J4C0B3Y
 * @version CommandAPI
 * @since 25/10/2024
 */
public class EnchantmentProvider extends Provider<Enchantment> {

    public EnchantmentProvider() {
        super(ProviderType.ARGUMENT, "enchantment");
    }

    @Override
    public Enchantment provide(CommandExecution execution, CommandArgument argument) {
        Enchantment enchantment = Enchantment.getByName(argument.getValue().toUpperCase());

        if (enchantment == null) {
            throw new ExitMessage(execution.getHandler().getLocale()
                .getInvalidEnum(argument.getValue(), suggest(execution.getActor(), argument))
            );
        }

        return enchantment;
    }

    @Override
    public List<String> suggest(Actor actor, CommandArgument argument) {
        List<String> suggestions = new ArrayList<>();

        for (Enchantment enchantment : Enchantment.values()) {
            suggestions.add(enchantment.getName());
        }

        return suggestions;
    }
}
