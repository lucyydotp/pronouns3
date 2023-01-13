package net.lucypoulton.pronouns.common.placeholder;

import net.lucypoulton.pronouns.api.Conjugator;
import net.lucypoulton.pronouns.api.PronounSet;
import net.lucypoulton.pronouns.common.ProNouns;
import net.lucypoulton.pronouns.common.placeholder.Placeholder.Result;
import net.lucypoulton.pronouns.common.util.EnumUtil;

import java.util.Locale;
import java.util.Set;
import java.util.function.BiFunction;
import java.util.function.Function;

public class Placeholders {

    private static String applyModifiers(PronounSet set, String value, String[] modifiers) {
        var out = value;
        for (final String mod : modifiers) {
            out = switch (mod.toLowerCase(Locale.ROOT)) {
                case "uppercase" -> out.toUpperCase(Locale.ROOT);
                case "lowercase" -> out.toLowerCase(Locale.ROOT);
                case "capital" -> switch (out.length()) {
                    case 0 -> out;
                    case 1 -> out.toUpperCase(Locale.ROOT);
                    default -> out.substring(0, 1).toUpperCase(Locale.ROOT) + out.substring(1).toLowerCase(Locale.ROOT);
                };
                case "nounset" -> set.equals(PronounSet.Builtins.UNSET) ? "" : out;
                default -> out;
            };
        }
        return out;
    }

    private final ProNouns plugin;

    public final Placeholder subjective = forPronoun("subjective", PronounSet::subjective);
    public final Placeholder objective = forPronoun("objective", PronounSet::objective);
    public final Placeholder possessiveAdj = forPronoun("possessiveadj", PronounSet::possessiveAdj);
    public final Placeholder possessive = forPronoun("possessive", PronounSet::possessive);
    public final Placeholder reflexive = forPronoun("reflexive", PronounSet::reflexive);
    public final Placeholder pronouns = forPronoun("pronouns", PronounSet::toString);
    public final Placeholder all = forPronoun("all", PronounSet::toFullString);

    public final Placeholder verb = forPronoun("verb", (set, value) -> {
        final var verb = EnumUtil.getByName(Conjugator.class, value[0]);
        return verb.map(conjugator -> Result.of(conjugator.conjugate(set.plural())))
                .orElseGet(() -> Result.fail("Unknown verb " + value[0]));
    });

    public final Placeholder conjugate = forPronoun("conj", (set, value) -> {
        if (value.length == 1) return Result.fail("Missing options for conjugation");
        return Result.of(value[set.plural() ? 1 : 0]);
    });

    private Placeholder forPronoun(String name, BiFunction<PronounSet, String[], Result> value) {
        return new Placeholder(name, ((sender, s) -> {
            if (sender.uuid().isEmpty()) return Result.fail("No player");
            final var set = plugin.store().sets(sender.uuid().get()).get(0);
            final var split = s.split("[_ ]");
            final var out = value.apply(set, split);
            if (out.success()) return Result.of(applyModifiers(set,out.message(), split));
            return out;
        }));
    }

    private Placeholder forPronoun(String name, Function<PronounSet, String> value) {
        return forPronoun(name, (set, str) -> Result.of(value.apply(set)));
    }

    public Placeholders(ProNouns plugin) {
        this.plugin = plugin;
    }

    public Set<Placeholder> placeholders() {
        return Set.of(subjective, objective, possessiveAdj, possessive, reflexive, pronouns, all, verb, conjugate);
    }
}
