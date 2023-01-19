package net.lucypoulton.pronouns.api.supplier;

import net.lucypoulton.pronouns.api.set.PronounSet;

import java.util.Set;

public record FixedSupplier(Set<PronounSet> sets) implements PronounSupplier {

    public FixedSupplier(PronounSet... sets) {
        this(Set.of(sets));
    }
    /**
     * Alias of {@link #sets()}
     */
    @Override
    public Set<PronounSet> get() {
        return sets();
    }
}
