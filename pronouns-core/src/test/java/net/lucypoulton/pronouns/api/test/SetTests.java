package net.lucypoulton.pronouns.api.test;

import net.lucypoulton.pronouns.api.set.PronounSet;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class SetTests {

    @Nested
    public class FormatterTests {
        @Test
        @DisplayName("Single sets call #toString")
        public void singleSet() {
            assertEquals(
                    PronounSet.format(List.of(PronounSet.Builtins.HE)),
                    "He/Him"
            );
            assertEquals(
                    PronounSet.format(List.of(PronounSet.Builtins.ANY)),
                    "Any"
            );
        }

        @Test
        @DisplayName("Multiple sets are combined")
        public void multipleSets() {
            assertEquals(
                    PronounSet.format(List.of(PronounSet.Builtins.HE, PronounSet.Builtins.ANY)),
                    "He/Any"
            );
        }

        @Test
        @DisplayName("Empty list throws")
        public void emptyListThrows() {
            assertThrows(IllegalArgumentException.class, () -> PronounSet.format(List.of()));
        }
    }

    @Nested
    public class SimpleSetTests {
        @Test
        @DisplayName("toString is in the format Sub/Obj")
        public void toStringFormat() {
            assertEquals(
                    PronounSet.from("one", "two", "three", "four", "five", false).toString(),
                    "One/Two");
        }
    }
}
