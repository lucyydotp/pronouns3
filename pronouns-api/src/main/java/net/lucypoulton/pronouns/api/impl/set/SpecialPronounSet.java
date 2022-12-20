package net.lucypoulton.pronouns.api.impl.set;

import net.lucypoulton.pronouns.api.PronounSet;

/**
 * A set that proxies pronouns from another but with a custom name.
 */
public class SpecialPronounSet implements PronounSet {

	private final PronounSet baseSet;
	private final String name;

	public SpecialPronounSet(final PronounSet baseSet, final String name) {
		this.baseSet = baseSet;
		this.name = name;
	}

	@Override
	public String subjective() {
		return baseSet.subjective();
	}

	@Override
	public String objective() {
		return baseSet.objective();
	}

	@Override
	public String possessiveAdj() {
		return baseSet.possessiveAdj();
	}

	@Override
	public String possessive() {
		return baseSet.possessive();
	}

	@Override
	public String reflexive() {
		return baseSet.reflexive();
	}

	@Override
	public boolean plural() {
		return baseSet.plural();
	}

	@Override
	public boolean includesPronoun(String pronoun) {
		return this.name.equalsIgnoreCase(pronoun);
	}

	@Override
	public String toString() {
		return this.name;
	}
}
