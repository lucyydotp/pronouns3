package net.lucypoulton.pronouns.api.set;

import org.jetbrains.annotations.NotNull;

import static net.lucypoulton.pronouns.api.util.StringUtils.capitalize;

record SimplePronounSet(
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
}

