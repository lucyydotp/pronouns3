package net.lucypoulton.pronouns.common.store;

import net.lucypoulton.pronouns.api.PronounStore;
import net.lucypoulton.pronouns.common.ProNouns;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

public class StoreFactory {
    private final Map<String, Supplier<PronounStore>> suppliers = new HashMap<>();

    public void register(String key, Supplier<PronounStore> supplier) {
        if (suppliers.putIfAbsent(key, supplier) != null) {
            throw new IllegalStateException("Store supplier with key " + key + " already registered");
        }
    }

    public PronounStore create(String key, ProNouns plugin) {
        return switch (key) {
            case "in_memory" -> new InMemoryPronounStore();
            case "file" -> new FilePronounStore(plugin.platform().dataDir());
            case "mysql" -> new MySqlPronounStore(plugin, plugin.platform().config().mysql());
            default -> {
                final var supplier = suppliers.get(key);
                if (supplier == null) throw new IllegalStateException("Unknown pronoun store type " + key);
                yield supplier.get();
            }
        };
    }
}
