package net.lucypoulton.pronouns.fabric;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;
import net.lucypoulton.pronouns.api.PronounStore;
import net.lucypoulton.pronouns.common.ProNouns;
import net.lucypoulton.pronouns.common.store.InMemoryPronounStore;
import net.lucypoulton.pronouns.fabric.nms.FabricPlatform;

public class ProNounsFabric implements ModInitializer {

    private final PronounStore store = new InMemoryPronounStore();

    @Override
    public void onInitialize() {
        final var platform = new FabricPlatform();
        final var plugin = new ProNouns(platform);
        ServerLifecycleEvents.SERVER_STARTING.register(platform::setServer);
    }
}
