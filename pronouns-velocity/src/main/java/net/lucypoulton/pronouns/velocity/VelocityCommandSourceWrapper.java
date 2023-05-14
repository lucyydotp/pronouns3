package net.lucypoulton.pronouns.velocity;

import com.velocitypowered.api.command.CommandSource;
import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.audience.ForwardingAudience;
import net.kyori.adventure.identity.Identity;
import net.lucypoulton.pronouns.common.platform.CommandSender;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;
import java.util.UUID;

public record VelocityCommandSourceWrapper(CommandSource source) implements CommandSender, ForwardingAudience.Single {

    @Override
    public Optional<UUID> uuid() {
        return source.get(Identity.UUID);
    }

    @Override
    public String name() {
        return source.get(Identity.NAME).orElse("Console");
    }

    @Override
    public boolean hasPermission(String permission) {
        return source.hasPermission(permission);
    }

    @Override
    public @NotNull Audience audience() {
        return source;
    }
}
