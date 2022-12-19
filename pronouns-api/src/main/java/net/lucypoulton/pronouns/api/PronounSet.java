package net.lucypoulton.pronouns.api;

import net.lucypoulton.pronouns.api.impl.set.SimplePronounSet;
import org.jetbrains.annotations.Contract;

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

    /**
     * Creates a new pronoun set, with {@link #plural()} set to false.
     * @see #from(String, String, String, String, String, boolean)
     */
    @Contract(value = "_, _, _, _, _ -> new", pure = true)
    static PronounSet from(String subjective, String objective, String possessiveAdj, String possessive, String reflexive) {
        return from(subjective, objective, possessiveAdj, possessive, reflexive, false);
    }

    /**
     * Creates a new pronoun set.
     */
    @Contract(value = "_, _, _, _, _, _ -> new", pure = true)
    static PronounSet from(String subjective, String objective, String possessiveAdj, String possessive, String reflexive, boolean plural) {
        return new SimplePronounSet(subjective, objective, possessiveAdj, possessive, reflexive, plural);
    }
}
