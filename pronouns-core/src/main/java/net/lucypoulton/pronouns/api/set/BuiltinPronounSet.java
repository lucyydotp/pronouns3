package net.lucypoulton.pronouns.api.set;

import org.jetbrains.annotations.NotNull;

import java.util.Locale;

import static net.lucypoulton.pronouns.api.util.StringUtils.capitalize;

/**
 * A built-in pronoun set.
 */
record BuiltinPronounSet(
        @NotNull String subjective,
        @NotNull String objective,
        @NotNull String possessiveAdj,
        @NotNull String possessive,
        @NotNull String reflexive,
        boolean plural
) implements PronounSet {
    @Override
    public String toString() {
        return capitalize(subjective()) + "/" + capitalize(objective());
    }

    /**
     * As this set is built-in, the parser is able to parse it from just a single pronoun.
     */
    @Override
    public String toFullString() {
        return subjective.toLowerCase(Locale.ROOT);
    }
}
