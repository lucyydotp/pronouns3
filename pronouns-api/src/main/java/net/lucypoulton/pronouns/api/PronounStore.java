package net.lucypoulton.pronouns.api;

import org.jetbrains.annotations.NotNull;

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
     * @return a list of pronouns a player has set, or a singleton list of {@link PronounSet.Builtins#UNSET} if none have been set.
     */
    List<PronounSet> sets(UUID player);

    /**
     * Sets a player's pronouns.
     *
     * @param player the player's UUID
     * @param sets   the pronouns to set. Each entry must be unique. If the provided list is empty, then clear the user's pronouns.
     */
    void set(UUID player, @NotNull List<PronounSet> sets);
}
