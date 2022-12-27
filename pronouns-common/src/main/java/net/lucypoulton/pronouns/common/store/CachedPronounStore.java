package net.lucypoulton.pronouns.common.store;

import net.lucypoulton.pronouns.api.PronounStore;

import java.util.UUID;

/**
 * A pronoun store that allows for caching.
 */
public interface CachedPronounStore extends PronounStore {
    /**
     * Called when a player joins. Use this method to retrieve data from a source.
     * This method will always be called on a thread where it is safe to block.
     * @param uuid the uuid of the player that joined
     */
    void onPlayerJoin(UUID uuid);

    /**
     * Called when a player joins. Use this method to push data to a source.
     * This method will always be called on a thread where it is safe to block.
     * @param uuid the uuid of the player that left
     */
    void onPlayerLeave(UUID uuid);
}
