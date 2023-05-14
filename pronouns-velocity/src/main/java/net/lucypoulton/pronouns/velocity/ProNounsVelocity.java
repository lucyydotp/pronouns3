package net.lucypoulton.pronouns.velocity;

import cloud.commandframework.CommandManager;
import cloud.commandframework.execution.CommandExecutionCoordinator;
import cloud.commandframework.velocity.VelocityCommandManager;
import com.google.inject.Inject;
import com.velocitypowered.api.event.Subscribe;
import com.velocitypowered.api.event.connection.DisconnectEvent;
import com.velocitypowered.api.event.connection.LoginEvent;
import com.velocitypowered.api.network.ProtocolVersion;
import com.velocitypowered.api.plugin.Plugin;
import com.velocitypowered.api.plugin.PluginContainer;
import com.velocitypowered.api.plugin.annotation.DataDirectory;
import com.velocitypowered.api.proxy.Player;
import com.velocitypowered.api.proxy.ProxyServer;
import net.kyori.adventure.text.Component;
import net.lucypoulton.pronouns.common.ProNouns;
import net.lucypoulton.pronouns.common.platform.CommandSender;
import net.lucypoulton.pronouns.common.platform.Platform;
import net.lucypoulton.pronouns.common.platform.config.Config;
import net.lucypoulton.pronouns.common.platform.config.PropertiesConfig;
import net.lucypoulton.pronouns.common.store.CachedPronounStore;
import net.lucypoulton.pronouns.common.store.StoreFactory;
import org.slf4j.Logger;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Plugin(
        id = "pronouns",
        name = "ProNouns",
        version = BuildConstants.VERSION
)
public class ProNounsVelocity implements Platform {

    private final ProxyServer server;
    private final org.slf4j.Logger logger;
    private final VelocityCommandManager<CommandSender> commandManager;
    private final Config config;

    private final Path dataDir;
    private final ProNouns plugin;

    @Inject
    public ProNounsVelocity(PluginContainer container, ProxyServer server, Logger logger, @DataDirectory Path dataDir) {
        this.server = server;
        this.logger = logger;
        // fixme - make this less jank
        this.commandManager = new VelocityCommandManager<>(container,
                server,
                CommandExecutionCoordinator.simpleCoordinator(),
                VelocityCommandSourceWrapper::new,
                sender -> ((VelocityCommandSourceWrapper) sender).source());
        this.dataDir = dataDir;
        try {
            Files.createDirectories(dataDir);
            this.config = new PropertiesConfig(dataDir().resolve("pronouns.cfg"), logger()).reloadConfig();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        logger.info("Loading ProNouns v" + BuildConstants.VERSION);
        plugin = new ProNouns(this);
        plugin.createStore(new StoreFactory());
    }

    @Override
    public CommandManager<CommandSender> commandManager() {
        return commandManager;
    }

    @Override
    public Optional<CommandSender> getPlayer(String name) {
        return server.getPlayer(name).map(VelocityCommandSourceWrapper::new);
    }

    @Override
    public Optional<CommandSender> getPlayer(UUID uuid) {
        return server.getPlayer(uuid).map(VelocityCommandSourceWrapper::new);
    }

    @Override
    public List<String> listPlayers() {
        return server.getAllPlayers().stream().map(Player::getUsername).toList();
    }

    @Override
    public String name() {
        return "Velocity";
    }

    @Override
    public String currentVersion() {
        return BuildConstants.VERSION;
    }

    @Override
    public String minecraftVersion() {
        return ProtocolVersion.SUPPORTED_VERSION_STRING;
    }

    @Override
    public Path dataDir() {
        return dataDir;
    }

    @Override
    public Config config() {
        return config;
    }

    @Override
    public Logger logger() {
        return logger;
    }

    @Subscribe
    public void onPlayerJoin(LoginEvent event) {
        if (plugin.store() instanceof CachedPronounStore cached) {
            plugin.executorService().execute(() -> cached.onPlayerJoin(event.getPlayer().getUniqueId()));
        }
    }

    @Subscribe
    public void onPlayerLeave(DisconnectEvent event) {
        if (plugin.store() instanceof CachedPronounStore cached) {
            plugin.executorService().execute(() -> cached.onPlayerLeave(event.getPlayer().getUniqueId()));
        }
    }

    @Override
    public void broadcast(Component component, String permission) {
        for (final var player: server.getAllPlayers()) {
            if (!player.hasPermission(permission)) continue;
            player.sendMessage(component);
        }
    }

    @Override
    public String[] commandAliases() {
        return new String[]{"pronounsvelocity", "pnvelocity", "pnv"};
    }
}
