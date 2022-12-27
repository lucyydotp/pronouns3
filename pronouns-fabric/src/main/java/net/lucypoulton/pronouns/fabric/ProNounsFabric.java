package net.lucypoulton.pronouns.fabric;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;
import net.fabricmc.fabric.api.networking.v1.ServerPlayConnectionEvents;
import net.lucypoulton.pronouns.api.PronounStore;
import net.lucypoulton.pronouns.common.ProNouns;
import net.lucypoulton.pronouns.common.store.CachedPronounStore;
import net.lucypoulton.pronouns.common.store.InMemoryPronounStore;
import net.lucypoulton.pronouns.common.store.StoreFactory;

import java.util.concurrent.CompletableFuture;

public class ProNounsFabric implements ModInitializer {

    private final PronounStore store = new InMemoryPronounStore();

    @Override
    public void onInitialize() {
        final var platform = new FabricPlatform();
        final var plugin = new ProNouns(platform);

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
