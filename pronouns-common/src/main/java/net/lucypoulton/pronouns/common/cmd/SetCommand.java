package net.lucypoulton.pronouns.common.cmd;

import cloud.commandframework.annotations.Argument;
import cloud.commandframework.annotations.CommandMethod;
import cloud.commandframework.annotations.Flag;
import cloud.commandframework.annotations.specifier.FlagYielding;
import net.lucypoulton.pronouns.api.PronounSet;
import net.lucypoulton.pronouns.common.ProNouns;
import net.lucypoulton.pronouns.common.platform.CommandSender;
import net.lucypoulton.pronouns.common.platform.Platform;

public class SetCommand {
    private final ProNouns plugin;
    private final Platform platform;

    public SetCommand(ProNouns plugin, Platform platform) {
        this.plugin = plugin;
        this.platform = platform;
    }

    @CommandMethod("pronouns set <pronouns>")
    public void execute(CommandSender sender,
                        @Argument("pronouns") @FlagYielding String value,
                        @Flag(value = "player", suggestions = "player") String targetPlayer
    ) {
        final var f = plugin.formatter();
        final var target = CommandUtils.getPlayerOrSender(sender, targetPlayer, platform);
        final var player = target.sender();
        if (player.uuid().isEmpty()) {
            sender.sendMessage(f.translated("pronouns.command.noPlayer"));
            return;
        }
        try {
            final var pronouns = plugin.parser().parse(value);
            plugin.store().set(player.uuid().get(), pronouns);
            sender.sendMessage(
                    f.translated("pronouns.command.set." + (target.isNotSender() ? "other" : "self"),
                            PronounSet.format(pronouns),
                            player.name()
                    )
            );
            return;
        } catch (IllegalArgumentException ignored) {
        }
        sender.sendMessage(
                f.translated("pronouns.command.set.badSet", value)
        );
    }
}
