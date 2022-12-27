package net.lucypoulton.pronouns.common.cmd;

import cloud.commandframework.annotations.Argument;
import cloud.commandframework.annotations.CommandMethod;
import net.lucypoulton.pronouns.api.PronounSet;
import net.lucypoulton.pronouns.common.ProNouns;
import net.lucypoulton.pronouns.common.platform.CommandSender;
import net.lucypoulton.pronouns.common.platform.Platform;
import org.jetbrains.annotations.Nullable;

public class GetCommand {
    private final Platform platform;
    private final ProNouns plugin;

    public GetCommand(ProNouns plugin, Platform platform) {
        this.plugin = plugin;
        this.platform = platform;
    }

    @CommandMethod("pronouns|pn get [player]")
    public void execute(CommandSender sender, @Argument(value = "player", suggestions = "player") @Nullable String target) {
        final var f = plugin.formatter();
        final var targetCommandSender = CommandUtils.getPlayerOrSender(sender, target, platform);
        final var targetPlayer = targetCommandSender.sender();
        if (targetPlayer.uuid().isEmpty()) {
            sender.sendMessage(f.translated("pronouns.command.noPlayer"));
            return;
        }
        final var pronouns = plugin.store().sets(targetPlayer.uuid().get());
        if (pronouns.size() == 1 && pronouns.get(0).equals(PronounSet.Builtins.UNSET)) {
            sender.sendMessage(
                    f.translated("pronouns.command.get.unset." + (targetCommandSender.isNotSender() ? "other" : "self"),
                            targetPlayer.name()));
            return;
        }
        sender.sendMessage(
                f.translated("pronouns.command.get." + (targetCommandSender.isNotSender() ? "other" : "self"),
                        PronounSet.format(pronouns),
                        targetPlayer.name()
                )
        );
    }
}
