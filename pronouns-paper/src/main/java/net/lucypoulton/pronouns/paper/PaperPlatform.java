package net.lucypoulton.pronouns.paper;

import cloud.commandframework.CommandManager;
import cloud.commandframework.execution.CommandExecutionCoordinator;
import cloud.commandframework.paper.PaperCommandManager;
import net.lucypoulton.pronouns.common.platform.CommandSender;
import net.lucypoulton.pronouns.common.platform.Platform;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.nio.file.Path;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class PaperPlatform implements Platform {

    private final ProNounsPaper plugin;
    private final CommandManager<CommandSender> manager;

    public PaperPlatform(ProNounsPaper plugin) {
        this.plugin = plugin;
        try {
            this.manager = new PaperCommandManager<>(plugin,
                    CommandExecutionCoordinator.simpleCoordinator(),
                    BukkitCommandSenderWrapper::new,
                    sender -> ((BukkitCommandSenderWrapper) sender).bukkitSender());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public String name() {
        return "Paper";
    }

    @Override
    public String currentVersion() {
        return plugin.getDescription().getVersion();
    }

    @Override
    public Path dataDir() {
        return plugin.getDataFolder().toPath();
    }

    @Override
    public CommandManager<CommandSender> commandManager() {
        return manager;
    }

    @Override
    public Optional<CommandSender> getPlayer(String name) {
        return Optional.ofNullable(Bukkit.getPlayer(name)).map(BukkitCommandSenderWrapper::new);
    }

    @Override
    public Optional<CommandSender> getPlayer(UUID uuid) {
        return Optional.ofNullable(Bukkit.getPlayer(uuid)).map(BukkitCommandSenderWrapper::new);
    }

    @Override
    public List<String> listPlayers() {
        return Bukkit.getOnlinePlayers().stream().map(Player::getName).toList();
    }
}
