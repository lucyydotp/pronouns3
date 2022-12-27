package net.lucypoulton.pronouns.common.cmd;

import cloud.commandframework.annotations.CommandMethod;
import cloud.commandframework.annotations.Hidden;
import net.kyori.adventure.text.Component;
import net.lucypoulton.pronouns.api.ProNounsPlugin;
import net.lucypoulton.pronouns.common.platform.CommandSender;
import net.lucypoulton.pronouns.common.platform.Platform;

public class DebugCommand {

    private static final String DEBUG_FORMAT = """
            ProNouns v%s
            Platform %s (%s)
            Store %s
            %s predefined sets""";

    private final ProNounsPlugin plugin;
    private final Platform platform;

    public DebugCommand(ProNounsPlugin plugin, Platform platform) {
        this.plugin = plugin;
        this.platform = platform;
    }

    @Hidden
    @CommandMethod("pronouns debug")
    public void execute(CommandSender sender) {
        sender.sendMessage(Component.text(
                String.format(DEBUG_FORMAT, platform.currentVersion(),
                        platform.name(), platform.getClass().getName(),
                        plugin.store().getClass().getName(),
                        plugin.store().predefined().get().size()
                )
        ));
    }
}
