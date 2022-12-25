package net.lucypoulton.pronouns.fabric.mixin;

import net.lucypoulton.pronouns.common.platform.CommandSender;
import net.minecraft.entity.Entity;
import net.minecraft.server.command.ServerCommandSource;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

import java.util.Optional;
import java.util.UUID;

@Mixin(ServerCommandSource.class)
public abstract class CommandSourceMixin implements CommandSender {

    @Shadow @Final private @Nullable Entity entity;

    @Shadow public abstract String getName();

    @Override
    public Optional<UUID> uuid() {
        return Optional.ofNullable(this.entity).map(Entity::getUuid);
    }

    @Override
    public String name() {
        return this.getName();
    }
}
