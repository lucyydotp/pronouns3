package net.lucypoulton.pronouns.common.cmd;

import cloud.commandframework.annotations.CommandMethod;
import net.lucypoulton.pronouns.common.ProNouns;
import net.lucypoulton.pronouns.common.platform.CommandSender;
import net.lucypoulton.pronouns.common.platform.Platform;

public class VersionCommand {
    private final ProNouns plugin;
    private final Platform platform;

    public VersionCommand(ProNouns plugin, Platform platform) {
        this.plugin = plugin;
        this.platform = platform;
    }

    @CommandMethod("pronouns version")
    public void execute(CommandSender sender) {
        sender.sendMessage(plugin.formatter().translated("pronouns.command.version",
                platform.currentVersion(),
                platform.name()
        ));
    }
}
