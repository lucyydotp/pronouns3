package net.lucypoulton.pronouns.common.cmd;

import cloud.commandframework.annotations.Argument;
import cloud.commandframework.annotations.CommandMethod;
import net.lucypoulton.pronouns.api.ProNounsPlugin;
import net.lucypoulton.pronouns.api.PronounSet;
import net.lucypoulton.pronouns.common.platform.CommandSender;
import net.lucypoulton.pronouns.common.platform.Platform;
import org.jetbrains.annotations.Nullable;

import static net.kyori.adventure.text.Component.text;
import static net.kyori.adventure.text.Component.translatable;

public class GetCommand {
    private final Platform platform;
    private final ProNounsPlugin plugin;

    public GetCommand(ProNounsPlugin plugin, Platform platform) {
        this.plugin = plugin;
        this.platform = platform;
    }

    @CommandMethod("pronouns|pn get [player]")
    public void execute(CommandSender sender, @Argument(value = "player", suggestions = "player") @Nullable String target) {
        final var targetCommandSender = CommandUtils.getPlayerOrSender(sender, target, platform);
        final var targetPlayer = targetCommandSender.sender();
        if (targetPlayer.uuid().isEmpty()) {
            sender.sendMessage(translatable("pronouns.command.noPlayer"));
            return;
        }
        final var pronouns = plugin.store().sets(targetPlayer.uuid().get());
        if (pronouns.size() == 1 && pronouns.get(0).equals(PronounSet.Builtins.UNSET)) {
            sender.sendMessage(translatable("pronouns.command.get.unset." + (targetCommandSender.isNotSender() ? "other" : "self"))
                    .args(text(targetPlayer.name())));
            return;
        }
        sender.sendMessage(
                translatable("pronouns.command.get." + (targetCommandSender.isNotSender() ? "other" : "self"))
                        .args(
                                text(PronounSet.format(pronouns)),
                                text(targetPlayer.name())
                        )

        );
    }
}
