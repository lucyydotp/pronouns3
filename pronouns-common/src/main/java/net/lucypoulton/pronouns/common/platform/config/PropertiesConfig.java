package net.lucypoulton.pronouns.common.platform.config;

import net.lucypoulton.pronouns.common.UpdateChecker.Channel;
import net.lucypoulton.pronouns.common.util.PropertiesUtil;
import org.jetbrains.annotations.Nullable;

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

    private boolean stats;

    private String store;

    private @Nullable String mysqlUrl;
    private @Nullable String mysqlUsername;
    private @Nullable String mysqlPassword;

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
        final var props = PropertiesUtil.fromFile(path);

        this.checkForUpdates = !getValue(props, "checkForUpdates", "true").equals("false");
        this.stats = !getValue(props, "stats", "true").equals("false");
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
        this.store = getValue(props, "store", null);

        // these have no defaults intentionally - we throw on retrieval as to not throw when not using mysql
        this.mysqlUrl = props.getProperty("mysql.url");
        this.mysqlUsername = props.getProperty("mysql.username");
        this.mysqlPassword = props.getProperty("mysql.password");

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
    public String store() {
        return store;
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

    @Override
    public boolean stats() {
        return stats;
    }

    @Override
    public MySqlConnectionInfo mysql() {
        if (mysqlUrl == null) throw new InvalidConfigurationException("Missing MySQL URL");
        if (mysqlUsername == null) throw new InvalidConfigurationException("Missing MySQL username");
        if (mysqlPassword == null) throw new InvalidConfigurationException("Missing MySQL password");

        return new MySqlConnectionInfo(mysqlUrl, mysqlUsername, mysqlPassword);
    }
}
