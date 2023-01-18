package net.lucypoulton.pronouns.common.test;

import net.lucypoulton.pronouns.api.PronounSet;
import net.lucypoulton.pronouns.api.PronounSet.Builtins;
import net.lucypoulton.pronouns.common.LegacyMigrator;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class LegacyMigrationTests {


    /*
    Any
it/it/it's/its/its/itself
xe/xem/xe's/xyr/xyrs/xemself
fae/faer/fae’re/faer/faers/faeself
ve/ver/ve’s/vis/vis/verself
xey/xem/xey's/xeir/xeirs/xemself
ze/hir/ze/hir/hirs/hirself
Ask
ey/em/ey're/eir/eirs/emself

     */

    @Test
    @DisplayName("Migrates predefined sets")
    public void migratesPredefined() {
        final var sets = Map.of(
                "she/her/she's/her/hers/herself", Builtins.SHE,
                "he/him/he's/his/his/himself", Builtins.HE,
                "they/them/they're/their/theirs/themself", Builtins.THEY,
                "any", Builtins.ANY,
                "ask", Builtins.ASK
        );

        sets.forEach((string, set) -> assertEquals(LegacyMigrator.fromLegacyString(string), set));
    }

    @Test
    @DisplayName("Migrates custom sets")
    public void migratesCustom() {
        final var sets = Map.of(
                "it/it/it's/its/its/itself", PronounSet.from("it", "it", "its", "its", "itself"),
                "xe/xem/xe's/xyr/xyrs/xemself", PronounSet.from("xe", "xem", "xyr", "xyrs", "xemself"),
                "fae/faer/fae’re/faer/faers/faeself", PronounSet.from("fae", "faer", "faer", "faers", "faeself", true),
                "xey/xem/xey're/xeir/xeirs/xemself", PronounSet.from("xey", "xem", "xeir", "xeirs", "xemself", true)
        );
        sets.forEach((string, set) -> assertEquals(LegacyMigrator.fromLegacyString(string), set));
    }
}
