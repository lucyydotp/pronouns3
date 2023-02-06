package net.lucypoulton.pronouns.common;

import net.lucypoulton.pronouns.api.set.PronounSet;
import net.lucypoulton.pronouns.api.PronounParser;
import org.yaml.snakeyaml.Yaml;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

public class LegacyMigrator {

    private LegacyMigrator() {}

    private static final PronounParser parser = new PronounParser(PronounSet.builtins);

    public static PronounSet fromLegacyString(final String string) {
        final var split = string.split("/");
        try {
            return parser.parse(split[0]).get(0);
        } catch (IllegalArgumentException ignored) {
        }

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

    public static final class MigrationException extends RuntimeException {
        public MigrationException(Throwable cause) {
            super(cause);
        }

        public MigrationException(String message) {
            super(message);
        }
    }

    private static final Yaml yaml = new Yaml();

    @SuppressWarnings("unchecked")
    public static Map<UUID, List<PronounSet>> fromYaml(final Path path) {
        try (final var file = Files.newInputStream(path)) {
            final var loaded = (Map<String, String[]>) yaml.load(file);
            final var out = new HashMap<UUID, List<PronounSet>>();
            loaded.forEach((uuid, sets) -> out.put(UUID.fromString(uuid), Arrays.stream(sets).map(LegacyMigrator::fromLegacyString).toList()));
            file.close();
            Files.move(path, path.resolveSibling("legacy-datastore.yml"));
            return out;
        } catch (ClassCastException e) {
            throw new MigrationException("Legacy datastore file is incorrectly formatted");
        } catch (IOException e) {
            throw new MigrationException(e);
        }
    }
}
