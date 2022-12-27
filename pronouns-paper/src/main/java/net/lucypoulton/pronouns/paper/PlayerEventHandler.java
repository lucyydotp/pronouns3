package net.lucypoulton.pronouns.paper;

import net.lucypoulton.pronouns.common.store.CachedPronounStore;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class PlayerEventHandler implements Listener {

    private final ProNounsPaper plugin;

    public PlayerEventHandler(ProNounsPaper plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent e) {
        if (plugin.getPlugin().store() instanceof CachedPronounStore cached) {
            Bukkit.getScheduler().runTaskAsynchronously(plugin, () -> cached.onPlayerJoin(e.getPlayer().getUniqueId()));
        }
    }

    @EventHandler
    public void onPlayerJoin(PlayerQuitEvent e) {
        if (plugin.getPlugin().store() instanceof CachedPronounStore cached) {
            Bukkit.getScheduler().runTaskAsynchronously(plugin, () -> cached.onPlayerLeave(e.getPlayer().getUniqueId()));
        }
    }
}
