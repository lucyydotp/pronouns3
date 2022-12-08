package net.lucypoulton.pronouns.api;

/**
 * A set of pronouns.
 */
public interface PronounSet {
	/**
	 * The subjective pronoun - "they".
	 */
	String subjective();

	/**
	 * The objective pronoun - "them".
	 */
	String objective();

	/**
	 * The possessive adjective - "their".
	 */
	String possessiveAdj();

	/**
	 * The possessive pronoun - "theirs".
	 */
	String possessive();

	/**
	 * The reflexive pronoun - "themselves".
	 */
	String reflexive();

	/**
	 * Whether this pronoun is conjugated as single or plural, i.e. "is" vs "are".
	 */
	boolean plural();

	/**
	 * Formats this set for display, by convention this will be `Subjective/Objective`.
	 */
	@Override
	String toString();

	default boolean includesPronoun(String pronoun) {
		return subjective().equalsIgnoreCase(pronoun) ||
				objective().equalsIgnoreCase(pronoun) ||
				possessiveAdj().equalsIgnoreCase(pronoun) ||
				possessive().equalsIgnoreCase(pronoun) ||
				reflexive().equalsIgnoreCase(pronoun);
	}
}
