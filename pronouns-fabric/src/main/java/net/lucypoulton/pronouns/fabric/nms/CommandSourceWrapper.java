package net.lucypoulton.pronouns.fabric.nms;

import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.audience.ForwardingAudience;
import net.lucypoulton.pronouns.common.platform.CommandSender;
import net.minecraft.entity.Entity;
import net.minecraft.server.command.ServerCommandSource;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;
import java.util.UUID;

public class CommandSourceWrapper implements CommandSender, ForwardingAudience.Single {
    private final ServerCommandSource source;

    public CommandSourceWrapper(ServerCommandSource source) {
        this.source = source;
    }

    public ServerCommandSource source() {
        return source;
    }

    @Override
    public @NotNull Audience audience() {
        return source;
    }

    @Override
    public Optional<UUID> uuid() {
        return Optional.ofNullable(source.getEntity()).map(Entity::getUuid);
    }

    @Override
    public String name() {
        return source.getName();
    }
}
