package net.lucypoulton.pronouns.common.cmd;

import cloud.commandframework.annotations.CommandMethod;
import cloud.commandframework.annotations.Flag;
import net.kyori.adventure.text.Component;
import net.lucypoulton.pronouns.common.ProNouns;
import net.lucypoulton.pronouns.common.platform.CommandSender;

public class UpdateCommand {

    private final ProNouns plugin;

    public UpdateCommand(ProNouns plugin) {
        this.plugin = plugin;
    }

    @CommandMethod("pronouns update")
    public void executeShow(CommandSender sender, @Flag("force") boolean force) {
        plugin.updateChecker().ifPresentOrElse(checker -> {
            if (force || checker.availableUpdate().isEmpty()) {
                sender.sendMessage(Component.translatable("pronouns.command.update"));
            }
            checker.checkForUpdates(force);
        }, () -> sender.sendMessage(Component.translatable("pronouns.update.disabled")));
    }
}
