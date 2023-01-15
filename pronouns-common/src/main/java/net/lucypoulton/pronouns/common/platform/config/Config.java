package net.lucypoulton.pronouns.common.platform.config;

import net.lucypoulton.pronouns.common.UpdateChecker;

/**
 * A configuration file.
 */
public interface Config {

    /**
     * Which store the plugin should use.
     */
    String store();

    /**
     * Whether the plugin should check for updates.
     */
    boolean checkForUpdates();

    UpdateChecker.Channel updateChannel();

    /**
     * A MiniMessage string that prefixes non-accent text.
     */
    String main();

    /**
     * A MiniMessage string that prefixes accent text.
     */
    String accent();

    /**
     * Whether to send anonymous statistics information about the plugin.
     */
    boolean stats();

    /**
     * MySQL connection info.
     */
    MySqlConnectionInfo mysql();

    void reload();

    record MySqlConnectionInfo(String jdbcUrl, String username, String password) { }
}
