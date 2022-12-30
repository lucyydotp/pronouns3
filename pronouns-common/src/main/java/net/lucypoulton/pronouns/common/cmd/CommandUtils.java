package net.lucypoulton.pronouns.common.cmd;

import cloud.commandframework.arguments.CommandArgument;
import cloud.commandframework.arguments.standard.StringArgument;
import net.lucypoulton.pronouns.common.message.ProNounsTranslations;
import net.lucypoulton.pronouns.common.platform.CommandSender;
import net.lucypoulton.pronouns.common.platform.Platform;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;

public class CommandUtils {
    public record GetPlayerResult(CommandSender sender, boolean isNotSender) {
    }

    public static GetPlayerResult getPlayerOrSender(CommandSender sender, @Nullable String playerName, Platform platform) {
        final var targetSender = Optional.ofNullable(playerName).flatMap(platform::getPlayer);
        return targetSender.map(commandSender -> new GetPlayerResult(commandSender, true))
                .orElseGet(() -> new GetPlayerResult(sender, false));
    }

    public static CommandArgument<CommandSender, String> optionalPlayer(String name, Platform platform) {
        return StringArgument.<CommandSender>builder(name)
                .asOptional()
                .withSuggestionsProvider((ctx, val) -> platform.listPlayers())
                .build();
    }

    public static String description(String name) {
        return ProNounsTranslations.translate("pronouns.command.desc." + name);
    }
}
