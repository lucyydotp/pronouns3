package net.lucypoulton.pronouns.api.test;

import net.lucypoulton.pronouns.api.impl.set.SimplePronounSet;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class SetTests {
    @Nested
    public class SimpleSetTests {
        @Test
        @DisplayName("toString is in the format Sub/Obj")
        public void toStringFormat() {
            assertEquals(
                    new SimplePronounSet("one", "two", "three", "four", "five", false).toString(),
                    "One/Two");
        }
    }
}
