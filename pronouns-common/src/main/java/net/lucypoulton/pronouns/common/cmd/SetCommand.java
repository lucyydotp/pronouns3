package net.lucypoulton.pronouns.common.cmd;

import cloud.commandframework.annotations.Argument;
import cloud.commandframework.annotations.CommandMethod;
import cloud.commandframework.annotations.Flag;
import cloud.commandframework.annotations.specifier.FlagYielding;
import net.kyori.adventure.text.Component;
import net.lucypoulton.pronouns.api.ProNounsPlugin;
import net.lucypoulton.pronouns.api.PronounSet;
import net.lucypoulton.pronouns.common.platform.CommandSender;
import net.lucypoulton.pronouns.common.platform.Platform;

import static net.kyori.adventure.text.Component.text;
import static net.kyori.adventure.text.Component.translatable;

public class SetCommand {
    private final ProNounsPlugin plugin;
    private final Platform platform;

    public SetCommand(ProNounsPlugin plugin, Platform platform) {
        this.plugin = plugin;
        this.platform = platform;
    }

    @CommandMethod("pronouns set <pronouns>")
    public void execute(CommandSender sender,
                        @Argument("pronouns") @FlagYielding String value,
                        @Flag(value = "player", suggestions = "player") String targetPlayer
    ) {
        final var target = CommandUtils.getPlayerOrSender(sender, targetPlayer, platform);
        final var player = target.sender();
        if (player.uuid().isEmpty()) {
            sender.sendMessage(Component.translatable("pronouns.command.noPlayer"));
            return;
        }
        try {
            final var pronouns = plugin.parser().parse(value);
            plugin.store().set(player.uuid().get(), pronouns);
            sender.sendMessage(target.isNotSender() ?
                    translatable("pronouns.command.set.other").args(
                            text(player.name()),
                            text(PronounSet.format(pronouns))
                    ) :
                    translatable("pronouns.command.set.self")
                            .args(text(PronounSet.format(pronouns)))
            );
            return;
        } catch (IllegalArgumentException ignored) {
        }
        sender.sendMessage(
                translatable("pronouns.command.set.badSet")
                        .args(text(value))
        );
    }
}
