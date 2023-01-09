package net.lucypoulton.pronouns.fabric;

import cloud.commandframework.CommandManager;
import cloud.commandframework.execution.CommandExecutionCoordinator;
import cloud.commandframework.fabric.FabricServerCommandManager;
import me.lucko.fabric.api.permissions.v0.Permissions;
import net.fabricmc.loader.api.FabricLoader;
import net.kyori.adventure.text.Component;
import net.lucypoulton.pronouns.common.platform.CommandSender;
import net.lucypoulton.pronouns.common.platform.Platform;
import net.lucypoulton.pronouns.common.platform.config.Config;
import net.lucypoulton.pronouns.common.platform.config.PropertiesConfig;
import net.minecraft.MinecraftVersion;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayerEntity;
import org.jetbrains.annotations.Nullable;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.logging.Logger;

public class FabricPlatform implements Platform {

    private MinecraftServer server;

    /* FIXME - cloud fabric currently does not have a way to customise permission checking
        so it falls back to op, which is not what we need
     */
    private final CommandManager<CommandSender> manager = new FabricServerCommandManager<>(
            CommandExecutionCoordinator.simpleCoordinator(),
            CommandSourceWrapper::new,
            source -> ((CommandSourceWrapper) source).source()
    );

    private final Config config;

    public FabricPlatform() {
        try {
            config = new PropertiesConfig(dataDir().resolve("pronouns.cfg"), logger()).reloadConfig();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void setServer(MinecraftServer server) {
        this.server = server;
    }

    private Optional<CommandSender> get(@Nullable ServerPlayerEntity entity) {
        return Optional.ofNullable(entity).map(e -> new CommandSourceWrapper(e.getCommandSource()));
    }

    @Override
    public String name() {
        return "Fabric";
    }

    @Override
    public String currentVersion() {
        return FabricLoader.getInstance().getModContainer("pronouns-fabric").orElseThrow()
                .getMetadata().getVersion().getFriendlyString();
    }

    @Override
    public String minecraftVersion() {
        return MinecraftVersion.create().getName();
    }

    @Override
    public Path dataDir() {
        return FabricLoader.getInstance().getConfigDir();
    }

    @Override
    public Config config() {
        return config;
    }

    @Override
    public Logger logger() {
        return Logger.getLogger("ProNouns");
    }

    @Override
    public void broadcast(Component component, String permission) {
        server.getCommandSource().sendMessage(component);
        for (final var player : server.getPlayerManager().getPlayerList()) {
            if (Permissions.check(player, permission)) player.sendMessage(component);
        }

    }

    @Override
    public CommandManager<CommandSender> commandManager() {
        return manager;
    }

    @Override
    public Optional<CommandSender> getPlayer(String name) {
        return get(server.getPlayerManager().getPlayer(name));
    }

    @Override
    public Optional<CommandSender> getPlayer(UUID uuid) {
        return get(server.getPlayerManager().getPlayer(uuid));
    }

    @Override
    public List<String> listPlayers() {
        return List.of(server.getPlayerNames());
    }
}
