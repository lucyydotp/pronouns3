package net.lucypoulton.pronouns.common;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.event.ClickEvent;
import net.kyori.adventure.text.event.HoverEvent;
import net.kyori.adventure.text.format.TextDecoration;
import net.lucypoulton.pronouns.common.platform.Platform;
import org.jetbrains.annotations.Nullable;

import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

public class UpdateChecker {

    public static class UpdateCheckException extends RuntimeException {

        public UpdateCheckException(String s) {
            super(s);
        }

        @Override
        public String toString() {
            return getMessage();
        }
    }

    public enum Channel {
        RELEASE("release"),
        BETA("release", "beta"),
        ALPHA("release", "beta", "alpha");


        private final List<String> types;

        Channel(String... types) {
            this.types = List.of(types);
        }

        public List<String> types() {
            return types;
        }
    }

    public record Version(String displayName, String name, String channel, String url) {
    }

    private static final String PROJECT_ID = "pronouns";
    private static final HttpClient client = HttpClient.newHttpClient();

    private final ProNouns plugin;
    private final Platform platform;
    private final URI uri;

    private final Channel updateChannel = Channel.ALPHA;

    private @Nullable Version availableUpdate;

    public Optional<Version> availableUpdate() {
        return Optional.ofNullable(availableUpdate);
    }

    public UpdateChecker(ProNouns plugin, Platform platform) {
        this.plugin = plugin;
        this.platform = platform;
        try {
            this.uri = new URI("https",
                    "api.modrinth.com",
                    "/v2/project/" + PROJECT_ID + "/version",
                    "loaders=[\"" + platform.name().toLowerCase(Locale.ROOT) + "\"]",
                    null
            );
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }

    private String userAgent() {
        return String.format("ProNouns/%s (%s) (github/lucypoulton)", platform.currentVersion(), platform.name());
    }

    private Version handle(JsonArray entries) {
        return entries.asList()
                .stream()
                .map(JsonElement::getAsJsonObject)
                .map(v -> new Version(
                        v.get("name").getAsString(),
                        v.get("version_number").getAsString(),
                        v.get("version_type").getAsString(),
                        "https://modrinth.com/project/" + PROJECT_ID + "/version/" + v.get("id").getAsString()
                ))
                .filter(v -> updateChannel.types().contains(v.channel))
                .findFirst()
                .orElseThrow(() -> new UpdateCheckException(
                        String.format("No versions are available for %s (%s)",
                                platform.name(),
                                updateChannel.name().toLowerCase(Locale.ROOT))
                ));
    }

    private void broadcast(final Version version) {
        final var f = plugin.formatter();
        if (platform.currentVersion().equals(version.name)) return;
        this.availableUpdate = version;
        platform.broadcast(Component.translatable(
                        "pronouns.update").args(
                        f.accent(version.displayName),
                        f.accent("Modrinth")
                                .clickEvent(ClickEvent.openUrl(version.url))
                                .hoverEvent(HoverEvent.showText(Component.text(version.url + "\nClick to open")))
                                .decorate(TextDecoration.UNDERLINED)
                ),
                "pronouns.update"
        );
    }

    public void checkForUpdates(boolean force) {
        if (this.availableUpdate != null && !force) {
            broadcast(this.availableUpdate);
            return;
        }
        final var req = HttpRequest.newBuilder()
                .uri(uri)
                .header("User-Agent", userAgent())
                .GET()
                .build();
        client.sendAsync(req, HttpResponse.BodyHandlers.ofString())
                .thenApply(r -> {
                    if (r.statusCode() / 100 != 2) {
                        // something went wrong
                        throw new UpdateCheckException("HTTP " + r.statusCode() + " " + uri);
                    }
                    return JsonParser.parseString(r.body()).getAsJsonArray();
                })
                .thenApply(this::handle)
                .thenAccept(this::broadcast)
                .exceptionally(ex -> {
                    platform.logger().warning("Failed to check for updates: " +
                            (ex.getCause() == null ? ex : ex.getCause()).toString());
                    return null;
                });
    }
}
