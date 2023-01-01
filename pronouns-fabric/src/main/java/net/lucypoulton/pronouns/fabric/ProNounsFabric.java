package net.lucypoulton.pronouns.fabric;

import eu.pb4.placeholders.api.PlaceholderResult;
import eu.pb4.placeholders.api.Placeholders;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;
import net.fabricmc.fabric.api.networking.v1.ServerPlayConnectionEvents;
import net.lucypoulton.pronouns.common.ProNouns;
import net.lucypoulton.pronouns.common.platform.CommandSender;
import net.lucypoulton.pronouns.common.store.CachedPronounStore;
import net.lucypoulton.pronouns.common.store.StoreFactory;
import net.minecraft.util.Identifier;

import java.util.Optional;
import java.util.concurrent.CompletableFuture;

public class ProNounsFabric implements ModInitializer {
    @Override
    public void onInitialize() {
        final var platform = new FabricPlatform();
        final var plugin = new ProNouns(platform);

        for (final var placeholder : plugin.placeholders().placeholders()) {
            Placeholders.register(new Identifier("pronouns", placeholder.name()),
                    (context, argument) -> {
                final var player = Optional.ofNullable(context.player())
                        .flatMap(s -> platform.getPlayer(s.getUuid()))
                        .orElse(CommandSender.EMPTY);

                        final var result = placeholder.function().apply(player, argument);
                        return result.success() ?
                                PlaceholderResult.value(result.message()) :
                                PlaceholderResult.invalid(result.message());
                    }
            );
        }

        ServerLifecycleEvents.SERVER_STARTED.register(server -> {
            platform.setServer(server);
            final var factory = new StoreFactory();
            factory.register("nbt", () -> NBTPronounStore.server(server));
            plugin.createStore(factory);
        });
        ServerPlayConnectionEvents.JOIN.register((handler, packetSender, server) -> {
            if (!(plugin.store() instanceof CachedPronounStore cached)) return;
            CompletableFuture.runAsync(() -> cached.onPlayerJoin(handler.getPlayer().getUuid()));
        });
        ServerPlayConnectionEvents.DISCONNECT.register((handler, server) -> {
            if (!(plugin.store() instanceof CachedPronounStore cached)) return;
            CompletableFuture.runAsync(() -> cached.onPlayerLeave(handler.getPlayer().getUuid()));
        });
    }
}
