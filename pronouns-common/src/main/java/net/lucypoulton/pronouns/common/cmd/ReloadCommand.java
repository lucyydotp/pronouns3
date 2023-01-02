package net.lucypoulton.pronouns.common.cmd;

import cloud.commandframework.Command;
import cloud.commandframework.meta.CommandMeta;
import net.lucypoulton.pronouns.common.ProNouns;
import net.lucypoulton.pronouns.common.platform.CommandSender;
import net.lucypoulton.pronouns.common.platform.ProNounsPermission;

public class ReloadCommand implements ProNounsCommand {

    private final ProNouns plugin;

    public ReloadCommand(ProNouns plugin) {
        this.plugin = plugin;
    }

    @Override
    public Command.Builder<CommandSender> build(Command.Builder<CommandSender> builder) {
        return builder.literal("reload")
                .meta(CommandMeta.DESCRIPTION, CommandUtils.description("reload"))
                .permission(ProNounsPermission.RELOAD.key)
                .handler(ctx -> {
                    ctx.getSender().sendMessage(plugin.formatter().translated("pronouns.command.reload"));
                    plugin.reload();
                });
    }
}
