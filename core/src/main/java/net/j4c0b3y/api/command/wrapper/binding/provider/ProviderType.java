package net.j4c0b3y.api.command.wrapper.binding.provider;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * @author J4C0B3Y
 * @version CommandAPI
 * @since 30/08/2024
 */
@Getter
@RequiredArgsConstructor
public enum ProviderType {
    ARGUMENT(true),
    CONTEXT(false);

    private final boolean consumer;
}
