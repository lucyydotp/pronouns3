package net.lucypoulton.pronouns.api.supplier;

import net.lucypoulton.pronouns.api.set.PronounSet;

import java.util.Set;
import java.util.function.Supplier;

/**
 * A supplier of predefined pronoun sets.
 */
@FunctionalInterface
public interface PronounSupplier extends Supplier<Set<PronounSet>> {
}
