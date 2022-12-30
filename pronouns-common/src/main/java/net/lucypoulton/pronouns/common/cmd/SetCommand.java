package net.lucypoulton.pronouns.common.cmd;

import cloud.commandframework.ArgumentDescription;
import cloud.commandframework.Command;
import cloud.commandframework.arguments.flags.CommandFlag;
import cloud.commandframework.arguments.standard.StringArgument;
import cloud.commandframework.meta.CommandMeta;
import net.lucypoulton.pronouns.api.PronounSet;
import net.lucypoulton.pronouns.common.ProNouns;
import net.lucypoulton.pronouns.common.platform.CommandSender;
import net.lucypoulton.pronouns.common.platform.ProNounsPermission;
import net.lucypoulton.pronouns.common.platform.Platform;
import org.jetbrains.annotations.Nullable;

public class SetCommand implements ProNounsCommand {
    private final ProNouns plugin;
    private final Platform platform;

    public SetCommand(ProNouns plugin, Platform platform) {
        this.plugin = plugin;
        this.platform = platform;
    }

    public void execute(CommandSender sender, String value, @Nullable String targetPlayer) {
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

    @Override
    public Command.Builder<CommandSender> build(Command.Builder<CommandSender> builder) {
        return builder.literal("set")
                .meta(CommandMeta.DESCRIPTION, CommandUtils.description("set"))
                .permission(ProNounsPermission.SET.key)
                .argument(StringArgument.greedyFlagYielding("pronouns"))
                .flag(CommandFlag.builder("player")
                        .withPermission(cloud.commandframework.permission.Permission.of(ProNounsPermission.SET_OTHER.key))
                        .withDescription(ArgumentDescription.of("A username of a player to set pronouns for."))
                        .withArgument(
                                CommandUtils.optionalPlayer("player", platform))
                ).handler(ctx -> execute(ctx.getSender(),
                        ctx.get("pronouns"),
                        ctx.getOrDefault("player", null)));
    }
}
