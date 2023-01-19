package net.lucypoulton.pronouns.common.cmd;

import cloud.commandframework.Command;
import cloud.commandframework.meta.CommandMeta;
import net.lucypoulton.pronouns.api.set.PronounSet;
import net.lucypoulton.pronouns.common.ProNouns;
import net.lucypoulton.pronouns.common.platform.CommandSender;
import net.lucypoulton.pronouns.common.platform.Platform;
import net.lucypoulton.pronouns.common.platform.ProNounsPermission;
import org.jetbrains.annotations.Nullable;

public class GetCommand implements ProNounsCommand {
    private final Platform platform;
    private final ProNouns plugin;

    public GetCommand(ProNouns plugin, Platform platform) {
        this.plugin = plugin;
        this.platform = platform;
    }

    public void execute(CommandSender sender, @Nullable String target) {
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

    @Override
    public Command.Builder<CommandSender> build(Command.Builder<CommandSender> builder) {
        return builder.literal("get")
                .meta(CommandMeta.DESCRIPTION, CommandUtils.description("get"))
                .permission(ProNounsPermission.GET.key)
                .argument(CommandUtils.optionalPlayer("player", platform))
                .handler(ctx -> execute(ctx.getSender(), ctx.getOrDefault("player", null)));
    }
}
