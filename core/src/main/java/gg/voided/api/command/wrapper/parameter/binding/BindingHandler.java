package gg.voided.api.command.wrapper.parameter.binding;

import gg.voided.api.command.wrapper.parameter.CommandParameter;
import gg.voided.api.command.wrapper.parameter.provider.Provider;

import java.util.*;

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

        for (ParameterBinding<?> binding : reversed(bindings)) {
            if (binding.provides(parameter)) {
                return binding.getProvider();
            }
        }

        return null;
    }

    private <T> List<T> reversed(List<T> list) {
        List<T> reversed = new ArrayList<>(list);
        Collections.reverse(reversed);
        return reversed;
    }
}
