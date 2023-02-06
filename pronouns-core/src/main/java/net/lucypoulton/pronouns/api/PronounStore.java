package net.lucypoulton.pronouns.api;

import net.lucypoulton.pronouns.api.supplier.PronounSupplier;
import net.lucypoulton.pronouns.api.set.PronounSet;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * A persistent store for players' pronouns.
 */
public interface PronounStore {

    List<PronounSet> UNSET_LIST = List.of(PronounSet.Builtins.UNSET);

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

    /**
     * Sets a player's pronouns if not already set.
     *
     * @param sets a map of player UUIDs to lists of unique pronoun sets.
     */
    void setAll(Map<UUID, List<PronounSet>> sets);

    /**
     * Gets a (possibly unmodifiable) map of every player and the pronouns they have set. This method may block.
     */
    Map<UUID, List<PronounSet>> dump();
}
