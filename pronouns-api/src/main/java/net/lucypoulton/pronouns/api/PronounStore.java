package net.lucypoulton.pronouns.api;

import java.util.List;
import java.util.UUID;

/**
 * A persistent store for players' pronouns.
 */
public interface PronounStore {

    /**
     * Gets all predefined pronoun sets.
     */
    PronounSupplier predefined();

    /**
     * Gets a player's set pronouns.
     *
     * @param player the player's UUID
     * @return a list of pronouns a player has set, or an empty list if none have been returned.
     */
    List<PronounSet> sets(UUID player);

    /**
     * Sets a player's pronouns.
     *
     * @param player the player's UUID
     * @param sets   the pronouns to set. Each entry must be unique.
     */
    void set(UUID player, List<PronounSet> sets);
}
