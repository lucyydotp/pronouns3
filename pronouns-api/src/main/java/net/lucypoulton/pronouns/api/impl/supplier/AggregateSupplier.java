package net.lucypoulton.pronouns.api.impl.supplier;

import net.lucypoulton.pronouns.api.PronounSet;
import net.lucypoulton.pronouns.api.PronounSupplier;

import java.util.Set;
public record AggregateSupplier(Set<PronounSupplier> providers) implements PronounSupplier {

    public AggregateSupplier(PronounSupplier... suppliers) {
        this(Set.of(suppliers));
    }
    @Override
    public Set<PronounSet> get() {
        return Set.copyOf(providers.stream().flatMap(p -> p.get().stream()).toList());
    }
}
