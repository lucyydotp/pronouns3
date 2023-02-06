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
     * Gets the version of Minecraft this server is providing for.
     */
    String minecraftVersion();

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

    /**
     * Returns whether this platform is capable of runnning ProNouns 2.x, and therefore may have data available to migrate.
     */
    default boolean migratable() {
        return false;
    }
}
