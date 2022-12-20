package net.lucypoulton.pronouns.api.test;

import net.lucypoulton.pronouns.api.PronounSet;
import net.lucypoulton.pronouns.api.impl.PronounParser;
import net.lucypoulton.pronouns.api.impl.set.SimplePronounSet;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class ParserTests {

    private final PronounSet[] sets = {
            new SimplePronounSet("one", "two", "three", "four", "five", false),
            new SimplePronounSet("six", "seven", "eight", "nine", "ten", false)
    };

    private final PronounParser parser = new PronounParser(() -> Set.of(sets));

    @Test
    @DisplayName("Parses predefined sets")
    void parsesPredefined() {
        for (final var part : List.of("one", "two", "three", "four", "five"))
            assertEquals(parser.parse(part).get(0), sets[0]);
    }

    @ParameterizedTest
    @DisplayName("Parses multiple predefined sets")
    @ValueSource(strings = {"one/six", "two/seven", "three/eight", "four/nine", "five/ten"})
    void parsesMultiple(final String input) {
        final var parsed = parser.parse(input);
        assertEquals(parsed.get(0), sets[0]);
        assertEquals(parsed.get(1), sets[1]);
    }

    @Test
    @DisplayName("Excludes repeated predefined sets")
    void excludesRepeated() {
        final var set = parser.parse("one/two/one/two/one");
        assertEquals(set.size(), 1);
        assertEquals(set.get(0), sets[0]);
    }

    @Test
    @DisplayName("Parses non-predefined singular sets")
    void parsesSingular() {
        final var set = new SimplePronounSet("a", "b", "c", "d", "e", false);
        assertEquals(parser.parse("a/b/c/d/e").get(0), set);
    }

    @Test
    @DisplayName("Parses non-predefined plural sets")
    void parsesPlural() {
        final var set = new SimplePronounSet("a", "b", "c", "d", "e", true);
        assertEquals(parser.parse("a/b/c/d/e:p").get(0), set);
    }

    @ParameterizedTest
    @DisplayName("Throws when an effectively zero input is given")
    @ValueSource(strings = {"", "/", " ", " / ", "arbitrary"})
    void throwsOnEffectivelyZero(String value) {
        assertThrows(IllegalArgumentException.class, () -> parser.parse(value));
    }
}
