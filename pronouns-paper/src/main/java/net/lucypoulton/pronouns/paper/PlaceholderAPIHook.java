package net.lucypoulton.pronouns.paper;

import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import net.lucypoulton.pronouns.common.ProNouns;
import net.lucypoulton.pronouns.common.platform.CommandSender;
import net.lucypoulton.pronouns.common.platform.Platform;
import org.bukkit.OfflinePlayer;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class PlaceholderAPIHook extends PlaceholderExpansion {
    private final ProNouns plugin;
    private final Platform platform;

    public PlaceholderAPIHook(ProNouns plugin, Platform platform) {
        this.plugin = plugin;
        this.platform = platform;
    }

    @Override
    public @NotNull String getIdentifier() {
        return "pronouns";
    }

    @Override
    public @NotNull String getAuthor() {
        return "lucyy.p";
    }

    @Override
    public @NotNull String getVersion() {
        return platform.currentVersion();
    }

    @Override
    public boolean persist() {
        return true;
    }

    @Override
    public @Nullable String onRequest(OfflinePlayer player, @NotNull String params) {
        final var split = params.split("_", 2);
        final var placeholder = plugin.placeholders().placeholders().stream()
                .filter(pl -> pl.name().equalsIgnoreCase(split[0]))
                .findFirst();
        if (placeholder.isEmpty()) return null;

        final var sender = player == null ?
                CommandSender.EMPTY :
                platform.getPlayer(player.getUniqueId()).orElse(CommandSender.EMPTY);

        return placeholder.get().function().apply(sender, split.length == 1 ? "" : split[1]).message();
    }
}
