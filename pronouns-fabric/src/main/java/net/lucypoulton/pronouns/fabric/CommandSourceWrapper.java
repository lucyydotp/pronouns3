package net.lucypoulton.pronouns.fabric;

import me.lucko.fabric.api.permissions.v0.Permissions;
import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.audience.ForwardingAudience;
import net.lucypoulton.pronouns.common.platform.CommandSender;
import net.lucypoulton.pronouns.common.platform.ProNounsPermission;
import net.minecraft.entity.Entity;
import net.minecraft.server.command.ServerCommandSource;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;
import java.util.UUID;

public record CommandSourceWrapper(ServerCommandSource source) implements CommandSender, ForwardingAudience.Single {

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

    @Override
    public boolean hasPermission(String permission) {
        ProNounsPermission perm = null;
        for (final var entry : ProNounsPermission.values()) {
            if (entry.key.equals(permission)) {
                perm = entry;
                break;
            }
        }
        return perm == null ?
                Permissions.check(source, permission) :
                Permissions.check(source, permission, perm.defaultLevel);
    }
}
