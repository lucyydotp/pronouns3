package net.lucypoulton.pronouns.common.cmd;

import cloud.commandframework.annotations.Argument;
import cloud.commandframework.annotations.CommandMethod;
import net.lucypoulton.pronouns.common.ProNouns;
import net.lucypoulton.pronouns.common.platform.CommandSender;
import net.lucypoulton.pronouns.common.platform.Platform;
import org.jetbrains.annotations.Nullable;

import java.util.List;

import static net.kyori.adventure.text.Component.translatable;

public class ClearCommand {
    private final ProNouns plugin;
    private final Platform platform;

    public ClearCommand(ProNouns plugin, Platform platform) {
        this.plugin = plugin;
        this.platform = platform;
    }

    @CommandMethod("pronouns clear [player]")
    public void execute(CommandSender commandSender, @Argument(value = "player", suggestions = "player") @Nullable String target) {
        final var sender = CommandUtils.getPlayerOrSender(commandSender, target, platform);
        final var player = sender.sender();
        if (player.uuid().isEmpty()) {
            player.sendMessage(translatable("pronouns.command.noPlayer"));
            return;
        }

        plugin.store().set(player.uuid().get(), List.of());
        commandSender.sendMessage(
                plugin.formatter().translated(
                        "pronouns.command.clear." + (sender.isNotSender() ? "other" : "self"),
                        player.name())
        );
    }
}
