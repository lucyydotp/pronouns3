package net.lucypoulton.pronouns.common.platform.config;

import net.lucypoulton.pronouns.common.UpdateChecker.Channel;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Locale;
import java.util.Properties;
import java.util.logging.Logger;

public class PropertiesConfig implements Config {
    private final Path path;
    private final Logger logger;

    private boolean checkForUpdates;
    private Channel updateChannel;

    private String main;
    private String accent;

    private String getValue(Properties properties, String key, String defaultValue) {
        final var out = properties.getOrDefault(key, defaultValue);
        if (out == null) throw new InvalidConfigurationException("Missing required value " + key);
        return (String) out;
    }

    public PropertiesConfig(Path path, Logger logger) {
        this.path = path;
        this.logger = logger;
    }

    public Config reloadConfig() throws IOException {
        if (!Files.exists(path)) {
            try (final var config = PropertiesConfig.class.getResourceAsStream("/pronouns-default.cfg")) {
                assert config != null;
                Files.copy(config, path);
            }
        }
        Properties props;
        try (final var file = Files.newInputStream(path)) {
            final var properties = new Properties();
            properties.load(file);
            props = properties;
        }

        this.checkForUpdates = !getValue(props, "checkForUpdates", "true").equals("false");
        final var channelString = getValue(props, "updateChannel", "release");
        this.updateChannel = switch (channelString.trim().toLowerCase(Locale.ROOT)) {
            case "alpha" -> Channel.ALPHA;
            case "beta" -> Channel.BETA;
            case "release" -> Channel.RELEASE;
            default -> {
                logger.warning("Unknown update channel " + channelString + ", falling back to release.");
                yield Channel.RELEASE;
            }
        };
        this.main = getValue(props, "main", "<reset>");
        this.accent = getValue(props, "accent", "<gradient:#fa9efa:#9dacfa>");

        return this;
    }

    @Override
    public void reload() {
        try {
            reloadConfig();
        } catch (IOException e) {
            logger.severe(e.getMessage());
        }
    }

    @Override
    public boolean checkForUpdates() {
        return checkForUpdates;
    }

    @Override
    public Channel updateChannel() {
        return updateChannel;
    }

    @Override
    public String main() {
        return main;
    }

    @Override
    public String accent() {
        return accent;
    }
}
