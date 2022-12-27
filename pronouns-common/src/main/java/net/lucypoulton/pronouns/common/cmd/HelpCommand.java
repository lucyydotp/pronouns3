package net.lucypoulton.pronouns.common.cmd;

import cloud.commandframework.CommandManager;
import cloud.commandframework.annotations.Argument;
import cloud.commandframework.annotations.CommandMethod;
import cloud.commandframework.annotations.specifier.Greedy;
import cloud.commandframework.meta.CommandMeta;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.format.TextDecoration;
import net.lucypoulton.pronouns.common.message.Formatter;
import net.lucypoulton.pronouns.common.platform.CommandSender;
import org.jetbrains.annotations.Nullable;

public class HelpCommand {
    private final CommandManager<CommandSender> manager;
    private static final TextComponent NO_DESCRIPTION = Component.text("No description provided").decorate(TextDecoration.ITALIC);

    public HelpCommand(CommandManager<CommandSender> manager) {
        this.manager = manager;
    }


    @CommandMethod("pronouns help [query]")
    public void execute(CommandSender sender, @Nullable @Greedy @Argument("query") String query) {
        var out = Component.empty().append(Component.translatable("pronouns.command.help.title"))
                .append(Component.newline());

        for (final var command : this.manager.commands()) {
            out = out.append(
                            new Formatter().accent(manager.commandSyntaxFormatter().apply(command.getArguments(), null))
                    ).append(Component.text(" - "))
                    .append(command.getCommandMeta().get(CommandMeta.DESCRIPTION)
                            .map(Component::text)
                            .orElse(NO_DESCRIPTION))
                    .append(Component.newline());
        }
        sender.sendMessage(out);
    }
}
