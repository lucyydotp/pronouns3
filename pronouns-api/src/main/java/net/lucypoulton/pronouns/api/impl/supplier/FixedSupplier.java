package net.lucypoulton.pronouns.api.impl.supplier;

import net.lucypoulton.pronouns.api.PronounSet;
import net.lucypoulton.pronouns.api.PronounSupplier;

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
