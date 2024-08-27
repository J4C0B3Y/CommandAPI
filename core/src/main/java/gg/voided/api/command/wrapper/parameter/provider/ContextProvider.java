package gg.voided.api.command.wrapper.parameter.provider;

/**
 * @author J4C0B3Y
 * @version CommandAPI
 * @since 8/27/24
 */
public abstract class ContextProvider<T> extends Provider<T> {

    public ContextProvider() {
        super(false);
    }
}
