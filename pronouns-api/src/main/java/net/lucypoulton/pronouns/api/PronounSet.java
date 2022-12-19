package net.lucypoulton.pronouns.api;

import net.lucypoulton.pronouns.api.impl.set.SimplePronounSet;
import net.lucypoulton.pronouns.api.impl.set.SpecialPronounSet;
import org.jetbrains.annotations.Contract;

import java.util.Set;

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
     *
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

    /**
     * A collection of builtin sets.
     */
    class Builtins {
        private Builtins() {}

        /**
         * The they/them pronoun set. Makes use of the more common plural "themselves" reflexive.
         */
        public static final PronounSet THEY = PronounSet.from("they", "them", "their", "theirs", "themselves", true);

        /**
         * The he/him pronoun set.
         */
        public static final PronounSet HE = PronounSet.from("he", "him", "his", "his", "himself", false);

        /**
         * The she/her pronoun set.
         */
        public static final PronounSet SHE = PronounSet.from("she", "her", "her", "hers", "herself", false);

        /**
         * A special set, proxied to they/them, that indicates any pronouns are acceptable.
         */
        public static final PronounSet ANY = new SpecialPronounSet(THEY, "Any");

        /**
         * A special set, proxied to they/them, that indicates to ask for the subject's pronouns..
         */
        public static final PronounSet ASK = new SpecialPronounSet(THEY, "Ask");

        /**
         * A special set, proxied to they/them, that indicates pronouns have not been set.
         */
        public static final PronounSet UNSET = new SpecialPronounSet(THEY, "Unset");

    }

    /**
     * A supplier for all pronound defined in {@link Builtins}, except for {@link Builtins#UNSET}.
     */
    PronounSupplier builtins = () -> Set.of(Builtins.THEY, Builtins.HE, Builtins.SHE, Builtins.ANY, Builtins.ASK);
}
