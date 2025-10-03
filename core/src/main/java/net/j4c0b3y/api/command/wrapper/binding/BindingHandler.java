package net.j4c0b3y.api.command.wrapper.binding;

import net.j4c0b3y.api.command.utils.ListUtils;
import net.j4c0b3y.api.command.wrapper.CommandParameter;
import net.j4c0b3y.api.command.wrapper.binding.provider.Provider;
import net.j4c0b3y.api.command.wrapper.binding.provider.impl.argument.ArrayProvider;

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
        Class<?> type = parameter.getType();

        if (type.isArray()) {
            return new ArrayProvider<>(assign(type.getComponentType()), type.getComponentType());
        }

        List<ParameterBinding<?>> bindings = this.bindings.get(parameter.getType());
        if (bindings == null) return null;

        for (ParameterBinding<?> binding : ListUtils.reversed(bindings)) {
            if (binding.provides(parameter)) {
                return binding.getProvider();
            }
        }

        return null;
    }

    @SuppressWarnings("unchecked")
    public <T> Provider<T> assign(Class<T> type) {
        List<ParameterBinding<?>> bindings = this.bindings.get(type);
        if (bindings == null) return null;

        for (ParameterBinding<?> binding : ListUtils.reversed(bindings)) {
            if (binding.getClassifiers().isEmpty()) {
                return (Provider<T>) binding.getProvider();
            }
        }

        return null;
    }

    public void clear() {
        bindings.clear();
    }
}
