package net.lucypoulton.pronouns.common.platform.config;

import net.lucypoulton.pronouns.common.UpdateChecker;

/**
 * A configuration file.
 */
public interface Config {
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

    void reload();
}
