package net.lucypoulton.pronouns.paper;

import net.lucypoulton.pronouns.common.ProNouns;
import org.bukkit.plugin.java.JavaPlugin;

public final class ProNounsPaper extends JavaPlugin {

    private ProNouns plugin;

    @Override
    public void onEnable() {
        if (!PaperDetector.IS_PAPER) {
            throw new RuntimeException("ProNouns requires Paper to run. Get it at https://papermc.io. The plugin will now disable itself.");
        }

        //noinspection ResultOfMethodCallIgnored
        getDataFolder().mkdir();

        plugin = new ProNouns(new PaperPlatform(this));
        getServer().getPluginManager().registerEvents(new PlayerEventHandler(this), this);
    }

    public ProNouns getPlugin() {
        return plugin;
    }
}
