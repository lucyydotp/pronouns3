package net.lucypoulton.pronouns.common.cmd;

import cloud.commandframework.ArgumentDescription;
import cloud.commandframework.Command;
import cloud.commandframework.arguments.flags.CommandFlag;
import cloud.commandframework.meta.CommandMeta;
import net.kyori.adventure.text.Component;
import net.lucypoulton.pronouns.common.ProNouns;
import net.lucypoulton.pronouns.common.platform.CommandSender;
import net.lucypoulton.pronouns.common.platform.ProNounsPermission;

public class UpdateCommand implements ProNounsCommand {

    private final ProNouns plugin;

    public UpdateCommand(ProNouns plugin) {
        this.plugin = plugin;
    }

    public void executeShow(CommandSender sender, boolean force) {
        plugin.updateChecker().ifPresentOrElse(checker -> {
            if (force || checker.availableUpdate().isEmpty()) {
                sender.sendMessage(Component.translatable("pronouns.command.update"));
            }
            checker.checkForUpdates(force);
        }, () -> sender.sendMessage(Component.translatable("pronouns.update.disabled")));
    }

    @Override
    public Command.Builder<CommandSender> build(Command.Builder<CommandSender> builder) {
        return builder.literal("update")
                .meta(CommandMeta.DESCRIPTION, CommandUtils.description("update"))
                .permission(ProNounsPermission.UPDATE.key)
                .flag(CommandFlag.builder("force")
                        .withAliases("f")
                        .withDescription(ArgumentDescription.of("Forces an update check, even if the plugin is aware of an available update already."))
                )
                .handler((ctx) -> executeShow(ctx.getSender(), ctx.flags().isPresent("force")));
    }
}
