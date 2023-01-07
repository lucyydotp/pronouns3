package net.lucypoulton.pronouns.common.cmd;

import cloud.commandframework.Command;
import cloud.commandframework.CommandManager;
import cloud.commandframework.arguments.CommandArgument;
import cloud.commandframework.arguments.StaticArgument;
import cloud.commandframework.arguments.standard.StringArgument;
import cloud.commandframework.meta.CommandMeta;
import net.kyori.adventure.text.Component;
import net.lucypoulton.pronouns.common.ProNouns;
import net.lucypoulton.pronouns.common.platform.CommandSender;
import org.jetbrains.annotations.Nullable;

public class HelpCommand implements ProNounsCommand {
    private final CommandManager<CommandSender> manager;
    private final ProNouns plugin;
    private static final String NO_DESCRIPTION = "<italic>lucy is an idiot and forgot a description";

    private Component format(Command<?> command) {
        final var name = command.getArguments().stream()
                .filter(arg -> arg instanceof StaticArgument<?>)
                .skip(1)
                .findFirst()
                .map(CommandArgument::getName)
                .orElse("");
        final var desc = command.getCommandMeta().get(CommandMeta.DESCRIPTION).orElse("");

        return plugin.formatter().translated(
                "pronouns.command.help.entry",
                false,
                name, desc.equals("") ? NO_DESCRIPTION : desc
        );
    }

    public HelpCommand(ProNouns plugin, CommandManager<CommandSender> manager) {
        this.manager = manager;
        this.plugin = plugin;
    }


    public void execute(CommandSender sender, @Nullable String query) {

        var out = Component.empty().append(plugin.formatter().translated("pronouns.command.help.title", false))
                .append(Component.newline());

        sender.sendMessage(out.append(
                this.manager.commands().stream()
                        .filter(cmd -> {
                            if (cmd.isHidden()) return false;
                            final var perm = cmd.getCommandPermission().toString();
                            return perm.equals("") || sender.hasPermission(perm);
                        })
                        .map(this::format)
                        .reduce(Component.empty(), (last, cur) -> last.append(Component.newline()).append(cur))
        ));
    }

    @Override
    public Command.Builder<CommandSender> build(Command.Builder<CommandSender> builder) {
        return builder.literal("help")
                .argument(StringArgument.optional("query"))
                .handler(ctx -> execute(
                        ctx.getSender(),
                        ctx.getOrDefault("query", null))
                );
    }
}
