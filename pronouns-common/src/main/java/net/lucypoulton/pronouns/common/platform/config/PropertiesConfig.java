package net.lucypoulton.pronouns.common.platform.config;

import net.lucypoulton.pronouns.common.UpdateChecker.Channel;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Locale;
import java.util.Properties;

public class PropertiesConfig implements Config {

    public static PropertiesConfig load(Path path) throws IOException {
        if (!Files.exists(path)) {
            try (final var config = PropertiesConfig.class.getResourceAsStream("/pronouns-default.cfg")) {
                assert config != null;
                Files.copy(config, path);
            }
        }
        try (final var file = Files.newInputStream(path)) {
            final var props = new Properties();
            props.load(file);
            return new PropertiesConfig(props);
        }
    }

    private final boolean checkForUpdates;
    private final Channel updateChannel;

    private String getValue(Properties properties, String key, String defaultValue) {
        final var out = properties.getOrDefault(key, defaultValue);
        if (out == null) throw new InvalidConfigurationException("Missing required value " + key);
        return (String) out;
    }

    private PropertiesConfig(Properties props) {
        this.checkForUpdates = !getValue(props, "checkForUpdates", "true").equals("false");
        final var channelString = getValue(props, "updateChannel", "release");
        this.updateChannel = switch (channelString.trim().toLowerCase(Locale.ROOT)) {
            case "alpha" -> Channel.ALPHA;
            case "beta" -> Channel.BETA;
            // todo - warn on unknown value
            default -> Channel.RELEASE;
        };
    }

    @Override
    public boolean checkForUpdates() {
        return checkForUpdates;
    }

    @Override
    public Channel updateChannel() {
        return updateChannel;
    }
}
