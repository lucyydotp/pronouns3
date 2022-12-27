package net.lucypoulton.pronouns.api;

import net.lucypoulton.pronouns.api.impl.set.BuiltinPronounSet;
import net.lucypoulton.pronouns.api.impl.set.SimplePronounSet;
import net.lucypoulton.pronouns.api.impl.set.SpecialPronounSet;
import net.lucypoulton.pronouns.api.impl.util.StringUtils;
import org.jetbrains.annotations.Contract;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

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

    /**
     * Gets this pronoun set in a format that can be parsed by a {@link net.lucypoulton.pronouns.api.impl.PronounParser}.
     */
    default String toFullString() {
        final StringBuilder out = new StringBuilder();
        out.append(subjective()).append("/")
                .append(objective()).append("/")
                .append(possessiveAdj()).append("/")
                .append(possessive()).append("/")
                .append(reflexive());
        if (plural()) out.append(":p");
        return out.toString();
    }

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
        public static final PronounSet THEY = new BuiltinPronounSet("they", "them", "their", "theirs", "themselves", true);

        /**
         * The he/him pronoun set.
         */
        public static final PronounSet HE = new BuiltinPronounSet("he", "him", "his", "his", "himself", false);

        /**
         * The she/her pronoun set.
         */
        public static final PronounSet SHE = new BuiltinPronounSet("she", "her", "her", "hers", "herself", false);

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

    /**
     * Formats a list of pronoun sets.
     * If there's one set in the list, returns the value of {@link #toString()}.
     * Otherwise, maps each set to either its subjective pronoun or its name depending on its type,
     * and joins them with a slash.
     * @param sets a list of pronoun sets, containing at least one element.
     */
    static String format(List<PronounSet> sets) {
        return switch (sets.size()) {
            case 0 -> throw new IllegalArgumentException("A list of 0 pronouns cannot be formatted");
            case 1 -> sets.get(0).toString();
            default -> sets.stream()
                            .map(set -> set instanceof SpecialPronounSet ? set.toString() : set.subjective())
                            .map(StringUtils::capitalize)
                            .collect(Collectors.joining("/"));
        };
    }
}
