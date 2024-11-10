package net.j4c0b3y.api.command.utils;

/**
 * @author J4C0B3Y
 * @version CommandAPI
 * @since 1/09/2024
 */
@FunctionalInterface
public interface CheckedRunnable {
    void run() throws Exception;
}
