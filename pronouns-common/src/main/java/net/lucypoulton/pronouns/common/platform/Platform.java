package net.lucypoulton.pronouns.common.platform;

import cloud.commandframework.CommandManager;
import net.lucypoulton.pronouns.api.PronounStore;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface Platform {
    PronounStore store();

    CommandManager<CommandSender> commandManager();

    Optional<CommandSender> getPlayer(String name);

    Optional<CommandSender> getPlayer(UUID uuid);

    List<String> listPlayers();
}
