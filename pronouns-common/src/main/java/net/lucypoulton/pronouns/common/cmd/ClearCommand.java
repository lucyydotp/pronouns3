package net.lucypoulton.pronouns.common.cmd;

import cloud.commandframework.annotations.Argument;
import cloud.commandframework.annotations.CommandMethod;
import net.lucypoulton.pronouns.api.PronounStore;
import net.lucypoulton.pronouns.common.platform.CommandSender;
import net.lucypoulton.pronouns.common.platform.Platform;
import org.jetbrains.annotations.Nullable;

import java.util.List;

import static net.kyori.adventure.text.Component.text;
import static net.kyori.adventure.text.Component.translatable;

public class ClearCommand {
    private final Platform platform;
    private final PronounStore store;

    public ClearCommand(PronounStore store, Platform platform) {
        this.platform = platform;
        this.store = store;
    }

    @CommandMethod("pronouns clear [player]")
    public void execute(CommandSender commandSender, @Argument(value = "player", suggestions = "player") @Nullable String target) {
        final var sender = CommandUtils.getPlayerOrSender(commandSender, target, platform);
        final var player = sender.sender();
        if (player.uuid().isEmpty()) {
            player.sendMessage(translatable("pronouns.command.noPlayer"));
            return;
        }

        store.set(player.uuid().get(), List.of());
        commandSender.sendMessage(
                translatable("pronouns.command.clear." + (sender.isNotSender() ? "other" : "self"))
                        .args(text(player.name()))
        );
    }
}
