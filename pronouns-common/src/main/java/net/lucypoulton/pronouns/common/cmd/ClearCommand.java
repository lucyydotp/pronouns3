package net.lucypoulton.pronouns.common.cmd;

import cloud.commandframework.Command;
import cloud.commandframework.arguments.flags.CommandFlag;
import cloud.commandframework.meta.CommandMeta;
import net.lucypoulton.pronouns.common.ProNouns;
import net.lucypoulton.pronouns.common.platform.CommandSender;
import net.lucypoulton.pronouns.common.platform.ProNounsPermission;
import net.lucypoulton.pronouns.common.platform.Platform;
import org.jetbrains.annotations.Nullable;

import java.util.List;

import static net.kyori.adventure.text.Component.translatable;

public class ClearCommand implements ProNounsCommand {
    private final ProNouns plugin;
    private final Platform platform;

    public ClearCommand(ProNouns plugin, Platform platform) {
        this.plugin = plugin;
        this.platform = platform;
    }

    public void execute(CommandSender commandSender, @Nullable String target) {
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

    @Override
    public Command.Builder<CommandSender> build(Command.Builder<CommandSender> builder) {
        return builder.literal("clear")
                .meta(CommandMeta.DESCRIPTION, CommandUtils.description("clear"))
                .permission(ProNounsPermission.SET.key)
                .flag(
                        CommandFlag.builder("player")
                                .withPermission(cloud.commandframework.permission.Permission.of(ProNounsPermission.SET_OTHER.key))
                                .withArgument(
                                        CommandUtils.optionalPlayer("player", platform)
                                ))
                .handler(ctx -> execute(ctx.getSender(), ctx.getOrDefault("player", null)));
    }
}
