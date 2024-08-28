package gg.voided.api.command.wrapper.parameter.provider;

/**
 * @author J4C0B3Y
 * @version CommandAPI
 * @since 27/08/2024
 */
public abstract class ContextProvider<T> extends Provider<T> {

    public ContextProvider(boolean async) {
        super(false, async);
    }

    public ContextProvider() {
        this(false);
    }
}
