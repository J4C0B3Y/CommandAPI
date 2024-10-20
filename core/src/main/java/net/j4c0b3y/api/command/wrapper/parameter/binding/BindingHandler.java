package net.j4c0b3y.api.command.wrapper.parameter.binding;

import net.j4c0b3y.api.command.utils.ListUtils;
import net.j4c0b3y.api.command.wrapper.parameter.CommandParameter;
import net.j4c0b3y.api.command.wrapper.parameter.provider.Provider;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author J4C0B3Y
 * @version CommandAPI
 * @since 27/08/2024
 */
public class BindingHandler {
    private final Map<Class<?>, List<ParameterBinding<?>>> bindings = new HashMap<>();

    public <T> void put(Class<T> type, ParameterBinding<T> binding) {
        bindings.computeIfAbsent(type, key -> new ArrayList<>()).add(binding);
    }

    public Provider<?> assign(CommandParameter parameter) {
        List<ParameterBinding<?>> bindings = this.bindings.get(parameter.getType());

        if (bindings == null) {
            return null;
        }

        for (ParameterBinding<?> binding : ListUtils.reversed(bindings)) {
            if (binding.provides(parameter)) {
                return binding.getProvider();
            }
        }

        return null;
    }
}
