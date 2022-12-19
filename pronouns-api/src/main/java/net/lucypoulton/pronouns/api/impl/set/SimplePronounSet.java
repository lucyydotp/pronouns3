package net.lucypoulton.pronouns.api.impl.set;

import net.lucypoulton.pronouns.api.PronounSet;
import org.jetbrains.annotations.NotNull;

public record SimplePronounSet(
		@NotNull String subjective,
		@NotNull String objective,
		@NotNull String possessiveAdj,
		@NotNull String possessive,
		@NotNull String reflexive,
		boolean plural
) implements PronounSet {
	@Override
	public String toString() {
		return subjective() + "/" + objective();
	}
}

