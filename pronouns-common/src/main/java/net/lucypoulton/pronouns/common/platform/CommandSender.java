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
}
