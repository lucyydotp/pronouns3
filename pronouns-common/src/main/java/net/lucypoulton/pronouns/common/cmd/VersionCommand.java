package net.lucypoulton.pronouns.common.cmd;

import cloud.commandframework.annotations.CommandMethod;
import net.kyori.adventure.text.Component;
import net.lucypoulton.pronouns.common.platform.CommandSender;
import net.lucypoulton.pronouns.common.platform.Platform;

public class VersionCommand {
    private final Platform platform;

    public VersionCommand(Platform platform) {
        this.platform = platform;
    }

    @CommandMethod("pronouns version")
    public void execute(CommandSender sender) {
        sender.sendMessage(Component.translatable("pronouns.command.version").args(
                Component.text(platform.currentVersion()),
                Component.text(platform.name())
        ));
    }
}
