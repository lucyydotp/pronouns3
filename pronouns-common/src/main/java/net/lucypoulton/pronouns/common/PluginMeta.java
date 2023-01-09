package net.lucypoulton.pronouns.common;

import net.lucypoulton.pronouns.common.platform.Platform;
import net.lucypoulton.pronouns.common.util.PropertiesUtil;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Properties;
import java.util.UUID;

public class PluginMeta {
    private final boolean isFirstRun;
    private final String identifier;
    private final String lastPluginVersion;
    private final Platform platform;
    private final Path filePath;

    private void save() {
        try (final var stream = Files.newOutputStream(filePath)) {
            final var props = new Properties();
            props.put("identifier", identifier);
            props.put("lastPluginVersion", platform.currentVersion());
            props.store(stream, """
                    ProNouns meta file.
                    Do not edit!
                    """);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public PluginMeta(final Platform platform) {
        this.platform = platform;
        filePath = platform.dataDir().resolve("pronouns-meta.cfg");
        try {
            CheckValidFile:
            if (Files.exists(filePath)) {
                final var fileData = PropertiesUtil.fromFile(filePath);
                final var identifier = (String) fileData.get("identifier");
                final var lastPluginVersion = (String) fileData.get("lastPluginVersion");
                if (identifier == null || lastPluginVersion == null) {
                    platform.logger().warning("""
                    Meta file has been tampered with!
                    pronouns-meta.cfg is not intended for editing.
                    Recreating it now.""");
                    break CheckValidFile;
                }

                this.identifier = identifier;
                this.lastPluginVersion = lastPluginVersion;
                this.isFirstRun = false;
                return;
            }
            identifier = UUID.randomUUID().toString();
            lastPluginVersion = platform.currentVersion();
            this.isFirstRun = true;
            save();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public String identifier() {
        return identifier;
    }

    public String lastPluginVersion() {
        return lastPluginVersion;
    }

    public boolean isFirstRun() {
        return isFirstRun;
    }
}
