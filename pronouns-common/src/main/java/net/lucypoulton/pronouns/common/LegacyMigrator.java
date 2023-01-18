package net.lucypoulton.pronouns.common;

import net.lucypoulton.pronouns.api.PronounSet;
import net.lucypoulton.pronouns.api.impl.PronounParser;

public class LegacyMigrator {

    private static final PronounParser parser = new PronounParser(PronounSet.builtins);

    public static PronounSet fromLegacyString(String string) {
        final var split = string.split("/");
        try {
            return parser.parse(split[0]).get(0);
        } catch (IllegalArgumentException ignored) {}

        if (split.length != 6) throw new IllegalArgumentException("Failed to parse legacy set " + string);

        return PronounSet.from(
                split[0],
                split[1],
                split[3],
                split[4],
                split[5],
                split[2].endsWith("re") // best guess at whether it's singular or plural
        );
    }
}
