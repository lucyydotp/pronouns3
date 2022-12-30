package net.lucypoulton.pronouns.common.platform;

import cloud.commandframework.CommandManager;
import net.kyori.adventure.text.Component;
import net.lucypoulton.pronouns.common.platform.config.Config;

import java.nio.file.Path;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.logging.Logger;

/**
 * Provides platform-specific methods.
 */
public interface Platform {

    /**
     * Gets the server's command manager.
     */
    CommandManager<CommandSender> commandManager();

    /**
     * Gets a CommandSender from an online player's username.
     */
    Optional<CommandSender> getPlayer(String name);

    /**
     * Gets a CommandSender from a player's UUID.
     */
    Optional<CommandSender> getPlayer(UUID uuid);

    /**
     * Lists all online players.
     */
    List<String> listPlayers();

    /**
     * Gets this platform's name.
     */
    String name();

    /**
     * Gets this plugin's version.
     */
    String currentVersion();

    /**
     * Gets the path to a directory that the plugin can use to store files.
     */
    Path dataDir();

    /**
     * Gets the plugin's config.
     */
    Config config();

    /**
     * Gets a logger that outputs to the console.
     */
    Logger logger();

    /**
     * Broadcasts a message to the console, and every player with a permission.
     */
    void broadcast(Component component, String permission);
}
