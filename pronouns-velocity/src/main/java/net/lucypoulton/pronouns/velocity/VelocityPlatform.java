package net.lucypoulton.pronouns.velocity;

import cloud.commandframework.CommandManager;
import com.velocitypowered.api.network.ProtocolVersion;
import com.velocitypowered.api.proxy.Player;
import com.velocitypowered.api.proxy.ProxyServer;
import net.kyori.adventure.text.Component;
import net.lucypoulton.pronouns.common.platform.CommandSender;
import net.lucypoulton.pronouns.common.platform.Platform;
import net.lucypoulton.pronouns.common.platform.config.Config;
import org.slf4j.Logger;

import java.nio.file.Path;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class VelocityPlatform implements Platform {

    private final ProxyServer server;
    private final org.slf4j.Logger logger;

    public VelocityPlatform(ProxyServer server, org.slf4j.Logger logger) {
        this.server = server;
        this.logger = logger;
    }

    @Override
    public CommandManager<CommandSender> commandManager() {
        return null;
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
        return null;
    }

    @Override
    public Config config() {
        return null;
    }

    @Override
    public Logger logger() {
        return logger;
    }

    @Override
    public void broadcast(Component component, String permission) {

    }
}
