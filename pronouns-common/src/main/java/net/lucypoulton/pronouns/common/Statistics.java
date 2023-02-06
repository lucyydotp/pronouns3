package net.lucypoulton.pronouns.common;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import net.lucypoulton.pronouns.common.platform.Platform;
import net.lucypoulton.pronouns.common.util.HttpUtil;

import java.net.URI;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Locale;

/**
 * Submits statistics to the stats server.
 */
class Statistics {
    private static final URI ENDPOINT = URI.create("https://pronouns-stats.lucypoulton.net/v1");

    private final ProNouns plugin;
    private final Platform platform;

    public Statistics(ProNouns plugin, Platform platform) {
        this.plugin = plugin;
        this.platform = platform;
    }

    private String statistics() {
        final var out = new JsonObject();
        out.addProperty("id", plugin.meta().identifier());
        out.addProperty("platform", platform.name().toLowerCase(Locale.ROOT));
        out.addProperty("pluginVersion", platform.currentVersion());
        out.addProperty("store", platform.config().store());
        out.addProperty("minecraftVersion", platform.minecraftVersion());

        return new Gson().toJson(out);
    }
    
    public void send() {
        final var req = HttpRequest.newBuilder()
                .uri(ENDPOINT)
                .header("User-Agent", HttpUtil.userAgent(platform))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(statistics()))
                .build();
        try {
            HttpUtil.client().send(req, HttpResponse.BodyHandlers.discarding());
        } catch (Exception ex) {
            platform.logger().warning("Failed to upload statistics. Please report this!" + ex);
        }
    }
}
