package net.lucypoulton.pronouns.common.store;

import net.lucypoulton.pronouns.api.set.PronounSet;
import net.lucypoulton.pronouns.api.supplier.PronounSupplier;
import net.lucypoulton.pronouns.api.PronounParser;
import net.lucypoulton.pronouns.common.util.PropertiesUtil;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.stream.Collectors;

public class FilePronounStore implements CachedPronounStore {

    private boolean isDirty = false;
    private final Path filePath;
    private final Map<UUID, List<PronounSet>> sets = new HashMap<>();

    private static final PronounParser parser = new PronounParser(PronounSet.builtins);

    public FilePronounStore(final Path filePath) {
        this.filePath = filePath.resolve("pronouns-store.properties");
        if (!Files.exists(this.filePath)) {
            save();
            return;
        }
        Properties properties;
        try {
            properties = PropertiesUtil.fromFile(this.filePath);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        properties.forEach((key, value) -> sets.put(UUID.fromString((String) key), parser.parse((String) value)));
    }

    public static void writeToFile(Map<UUID, List<PronounSet>> sets, Path path, String header) throws IOException {
        final var props = new Properties();
        sets.forEach(((uuid, pronounSets) -> props.put(uuid.toString(),
                pronounSets.stream()
                        .map(PronounSet::toFullString)
                        .collect(Collectors.joining("/"))
        )));
        try (final var outStream = Files.newOutputStream(path)) {
            props.store(outStream, header);
        }
    }

    private synchronized void save() {
        if (!isDirty) return;
        try {
            writeToFile(sets, filePath, "ProNouns storage file. This file should not be edited while the server is running");
            this.isDirty = false;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Stub - not needed
     */
    @Override
    public void onPlayerJoin(UUID uuid) {
    }

    @Override
    public void onPlayerLeave(UUID uuid) {
        this.save();
    }

    @Override
    public PronounSupplier predefined() {
        return PronounSet.builtins;
    }

    @Override
    public List<PronounSet> sets(UUID player) {
        return sets.getOrDefault(player, UNSET_LIST);
    }

    @Override
    public void set(UUID player, @NotNull List<PronounSet> sets) {
        if (sets.size() == 0) this.sets.remove(player);
        else this.sets.put(player, sets);
        this.isDirty = true;
        save();
    }

    @Override
    public Map<UUID, List<PronounSet>> dump() {
        return Collections.unmodifiableMap(sets);
    }
}
