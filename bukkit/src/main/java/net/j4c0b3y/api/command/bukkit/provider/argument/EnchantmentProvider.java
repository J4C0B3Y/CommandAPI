package net.j4c0b3y.api.command.bukkit.provider.argument;

import net.j4c0b3y.api.command.execution.CommandExecution;
import net.j4c0b3y.api.command.execution.argument.CommandArgument;
import net.j4c0b3y.api.command.wrapper.parameter.provider.Provider;
import net.j4c0b3y.api.command.wrapper.parameter.provider.ProviderType;
import org.bukkit.enchantments.Enchantment;

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
        return null;
    }
}
