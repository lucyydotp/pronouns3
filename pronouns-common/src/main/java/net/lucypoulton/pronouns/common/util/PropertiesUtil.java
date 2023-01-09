package net.lucypoulton.pronouns.common.util;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Properties;

public class PropertiesUtil {
    private PropertiesUtil() {
    }

    public static Properties fromFile(Path path) throws IOException {
        final var properties = new Properties();
        try (final var inStream = Files.newInputStream(path)) {
            properties.load(inStream);
        }
        return properties;
    }
}
