package net.lucypoulton.pronouns.common;

import net.kyori.adventure.translation.GlobalTranslator;
import net.lucypoulton.pronouns.api.ProNounsPlugin;
import net.lucypoulton.pronouns.api.PronounStore;
import net.lucypoulton.pronouns.api.impl.PronounParser;
import net.lucypoulton.pronouns.common.cmd.*;
import net.lucypoulton.pronouns.common.message.Formatter;
import net.lucypoulton.pronouns.common.message.ProNounsTranslations;
import net.lucypoulton.pronouns.common.placeholder.Placeholders;
import net.lucypoulton.pronouns.common.platform.Platform;
import net.lucypoulton.pronouns.common.store.StoreFactory;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Optional;

public class ProNouns implements ProNounsPlugin {

    private final PronounParser parser;
    private final Platform platform;
    private final Placeholders placeholders = new Placeholders(this);
    private PronounStore store;
    private @Nullable
    final UpdateChecker checker;
    private Formatter formatter;

    public ProNouns(Platform platform) {
        this.platform = platform;
        this.formatter = new Formatter(platform);
        this.parser = new PronounParser(() -> store.predefined().get());

        GlobalTranslator.translator().addSource(ProNounsTranslations.registry());
        final var commandManager = platform.commandManager();

        final var commands = List.of(
                new GetCommand(this, platform),
                new SetCommand(this, platform),
                new ClearCommand(this, platform),
                new VersionCommand(this, platform),
                new DebugCommand(this, platform),
                new UpdateCommand(this),
                new HelpCommand(this, commandManager),
                new ReloadCommand(this)
        );

        for (final var command : commands) {
            commandManager.command(
                    commandManager.commandBuilder("pronouns", "pn").apply(command::build)
            );
        }

        final var isDevelopmentVersion = platform.currentVersion().endsWith("-SNAPSHOT");
        if (!isDevelopmentVersion && platform.config().checkForUpdates()) {
            checker = new UpdateChecker(this, platform);
            checker.checkForUpdates(false);
        } else {
            checker = null;
            platform.logger().warning(isDevelopmentVersion ?
                    "Development version " + platform.currentVersion() + ", disabling update checker." :
                    ProNounsTranslations.translate("pronouns.update.disabled"));
        }
    }

    public void createStore(StoreFactory store) {
        this.store = store.create("nbt", platform);
    }

    public void reload() {
        platform.config().reload();
        this.formatter = new Formatter(platform);
    }

    @Override
    public PronounStore store() {
        return this.store;
    }

    @Override
    public PronounParser parser() {
        return parser;
    }

    public Formatter formatter() {
        return formatter;
    }

    public Optional<UpdateChecker> updateChecker() {
        return Optional.ofNullable(checker);
    }

    public Placeholders placeholders() {
        return placeholders;
    }
}
