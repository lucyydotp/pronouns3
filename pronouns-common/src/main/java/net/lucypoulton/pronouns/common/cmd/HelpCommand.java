package net.lucypoulton.pronouns.common.cmd;

import cloud.commandframework.Command;
import cloud.commandframework.CommandManager;
import cloud.commandframework.arguments.CommandArgument;
import cloud.commandframework.arguments.StaticArgument;
import cloud.commandframework.arguments.standard.StringArgument;
import cloud.commandframework.meta.CommandMeta;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.format.TextColor;
import net.kyori.adventure.text.format.TextDecoration;
import net.lucypoulton.pronouns.common.ProNouns;
import net.lucypoulton.pronouns.common.platform.CommandSender;
import org.jetbrains.annotations.Nullable;

public class HelpCommand implements ProNounsCommand {
    private final CommandManager<CommandSender> manager;
    private final ProNouns plugin;
    private static final TextComponent NO_DESCRIPTION = Component.text("lucy is an idiot and forgot a description").decorate(TextDecoration.ITALIC);

    private Component format(Command<?> command) {
        final var name = command.getArguments().stream()
                .filter(arg -> arg instanceof StaticArgument<?>)
                .skip(1)
                .findFirst()
                .map(CommandArgument::getName)
                .orElse("");
        final var desc = command.getCommandMeta().get(CommandMeta.DESCRIPTION).orElse("");

        return Component.text("/pn ")
                .append(plugin.formatter().accent(name))
                .append(Component.text(" - ").color(TextColor.color(0x777777)))
                .append(desc.equals("") ? NO_DESCRIPTION : Component.text(desc));
    }

    public HelpCommand(ProNouns plugin, CommandManager<CommandSender> manager) {
        this.manager = manager;
        this.plugin = plugin;
    }


    public void execute(CommandSender sender, @Nullable String query) {
        var out = Component.empty().append(Component.translatable("pronouns.command.help.title"))
                .append(Component.newline());

        sender.sendMessage(out.append(
                this.manager.commands().stream()
                        .filter(cmd -> sender.hasPermission(cmd.getCommandPermission().toString()))
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
