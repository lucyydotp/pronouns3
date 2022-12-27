package net.lucypoulton.pronouns.common.store;

import java.util.UUID;
import java.util.concurrent.CompletableFuture;

/**
 * A pronoun store that allows for caching.
 */
public interface CachedPronounStore {
    /**
     * Called when a player joins. Use this method to retrieve data from a source.
     * @param uuid the uuid of the player that joined
     */
    CompletableFuture<Void> onPlayerJoin(UUID uuid);

    /**
     * Called when a player joins. Use this method to push data to a source.
     * @param uuid the uuid of the player that left
     */
    CompletableFuture<Void> onPlayerLeave(UUID uuid);
}
