package net.lucypoulton.pronouns.fabric;

import net.lucypoulton.pronouns.api.PronounSet;
import net.lucypoulton.pronouns.api.PronounStore;
import net.lucypoulton.pronouns.api.PronounSupplier;
import org.jetbrains.annotations.NotNull;

import java.util.*;

public class InMemoryPronounStore implements PronounStore {

    private final Map<UUID, List<PronounSet>> storage = new HashMap<>();
    @Override
    public PronounSupplier predefined() {
        return PronounSet.builtins;
    }

    @Override
    public List<PronounSet> sets(UUID player) {
        final var sets = storage.get(player);
        return sets == null ? List.of(PronounSet.Builtins.UNSET) : sets;
    }

    @Override
    public void set(UUID player, @NotNull List<PronounSet> sets) {
        if (sets.size() == 0) storage.remove(player);
        else storage.put(player, sets);
    }
}
