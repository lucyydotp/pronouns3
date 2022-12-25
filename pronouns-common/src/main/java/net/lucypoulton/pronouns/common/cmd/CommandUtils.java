package net.lucypoulton.pronouns.common.cmd;

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
}
