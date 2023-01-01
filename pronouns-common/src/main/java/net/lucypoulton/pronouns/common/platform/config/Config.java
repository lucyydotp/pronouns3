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
     * A MiniMessage string to format accents.
     */
    String accent();
}
