package net.lucypoulton.pronouns.common.cmd;

import cloud.commandframework.Command;
import net.lucypoulton.pronouns.common.platform.CommandSender;

public interface ProNounsCommand {
    Command.Builder<CommandSender> build(Command.Builder<CommandSender> builder);
}
