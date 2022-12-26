package net.lucypoulton.pronouns.fabric;

import cloud.commandframework.CommandManager;
import cloud.commandframework.execution.CommandExecutionCoordinator;
import cloud.commandframework.fabric.FabricServerCommandManager;
import net.lucypoulton.pronouns.api.PronounStore;
import net.lucypoulton.pronouns.common.platform.CommandSender;
import net.lucypoulton.pronouns.common.platform.Platform;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayerEntity;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class FabricPlatform implements Platform {

    private MinecraftServer server;
    private final CommandManager<CommandSender> manager = new FabricServerCommandManager<>(
            CommandExecutionCoordinator.simpleCoordinator(),
            CommandSourceWrapper::new,
            source -> ((CommandSourceWrapper) source).source()
    );

    public void setServer(MinecraftServer server) {
        this.server = server;
    }

    private Optional<CommandSender> get(@Nullable ServerPlayerEntity entity) {
        return Optional.ofNullable(entity).map(e -> (CommandSender) e.getCommandSource());
    }

    @Override
    public PronounStore store() {
        return NBTPronounStore.server(server);
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
