package net.lucypoulton.pronouns.common.platform;

import cloud.commandframework.CommandManager;

import java.nio.file.Path;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

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
}
