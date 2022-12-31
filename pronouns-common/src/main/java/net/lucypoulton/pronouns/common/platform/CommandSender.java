package net.lucypoulton.pronouns.common.platform;

import net.kyori.adventure.audience.Audience;

import java.util.Optional;
import java.util.UUID;

/**
 * A source for commands.
 */
public interface CommandSender extends Audience {
    /**
     * If this sender is a player, their UUID, otherwise empty.
     */
    Optional<UUID> uuid();

    /**
     * A name for this sender.
     */
    String name();

    /**
     * Whether this sender has a given permission.
     * @see ProNounsPermission
     */
    boolean hasPermission(String permission);

    CommandSender EMPTY = new CommandSender() {
        @Override
        public Optional<UUID> uuid() {
            return Optional.empty();
        }

        @Override
        public String name() {
            return "[empty]";
        }

        @Override
        public boolean hasPermission(String permission) {
            return false;
        }
    };
}
