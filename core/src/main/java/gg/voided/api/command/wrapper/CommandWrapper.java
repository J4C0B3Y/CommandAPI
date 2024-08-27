package gg.voided.api.command.wrapper;

import gg.voided.api.command.CommandHandler;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * @author J4C0B3Y
 * @version CommandAPI
 * @since 27/08/2024
 */
@Getter
@RequiredArgsConstructor
public class CommandWrapper {
    private final CommandHandler handler;

}
