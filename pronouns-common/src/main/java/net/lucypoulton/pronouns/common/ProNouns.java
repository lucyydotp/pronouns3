package net.lucypoulton.pronouns.common;

import net.kyori.adventure.translation.GlobalTranslator;
import net.lucypoulton.pronouns.api.ProNounsPlugin;
import net.lucypoulton.pronouns.api.PronounStore;
import net.lucypoulton.pronouns.api.PronounParser;
import net.lucypoulton.pronouns.common.cmd.*;
import net.lucypoulton.pronouns.common.message.Formatter;
import net.lucypoulton.pronouns.common.message.ProNounsTranslations;
import net.lucypoulton.pronouns.common.placeholder.Placeholders;
import net.lucypoulton.pronouns.common.platform.Platform;
import net.lucypoulton.pronouns.common.store.StoreFactory;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class ProNouns implements ProNounsPlugin {

    private final ScheduledExecutorService executorService = Executors.newSingleThreadScheduledExecutor(r -> {
        final var thread = new Thread(r);
        thread.setName("ProNouns worker");
        return thread;
    });
    private final PronounParser parser;
    private final Platform platform;
    private final PluginMeta meta;
    private final Placeholders placeholders = new Placeholders(this);
    private PronounStore store;
    private @Nullable
    final UpdateChecker checker;
    private Formatter formatter;

    public ProNouns(Platform platform) {
        this.platform = platform;
        this.formatter = new Formatter(platform);
        this.parser = new PronounParser(() -> store.predefined().get());
        this.meta = new PluginMeta(platform);

        platform.logger().info(meta.identifier());

        GlobalTranslator.translator().addSource(ProNounsTranslations.registry());
        final var commandManager = platform.commandManager();

        if (meta.isFirstRun()) {
            platform.logger().info(ProNounsTranslations.translate("pronouns.first-launch"));
        }

        final var helpCommand = new HelpCommand(this, commandManager);

        final var commands = List.of(
                helpCommand,
                new GetCommand(this, platform),
                new SetCommand(this, platform),
                new ClearCommand(this, platform),
                new VersionCommand(this, platform),
                new DebugCommand(this, platform),
                new UpdateCommand(this),
                new ReloadCommand(this),
                new DumpCommand(this, platform),
                new MigrateCommand(this)
        );

        commandManager.commandBuilder("pronouns", "pn").apply(helpCommand::buildForRoot);
        for (final var command : commands) {
            if (!command.shouldRegister()) continue;
            commandManager.command(
                    commandManager.commandBuilder("pronouns", "pn").apply(command::build)
            );
        }

        final var isDevelopmentVersion = platform.currentVersion().endsWith("-SNAPSHOT");
        if (!isDevelopmentVersion && platform.config().checkForUpdates()) {
            checker = new UpdateChecker(this, platform);
            checker.checkForUpdates(false);
            executorService.scheduleAtFixedRate(
                    () -> checker.checkForUpdates(false),
                    0,
                    6,
                    TimeUnit.HOURS
            );

        } else {
            checker = null;
            platform.logger().warn(isDevelopmentVersion ?
                    "Development version " + platform.currentVersion() + ", disabling update checker." :
                    ProNounsTranslations.translate("pronouns.update.disabled"));
        }

        if (platform.config().stats()) {
            final var stats = new Statistics(this, platform);
            executorService.scheduleAtFixedRate(stats::send, 5, 15, TimeUnit.MINUTES);
        }
    }

    public void createStore(StoreFactory factory) {
        this.store = factory.create(platform.config().store().toLowerCase(Locale.ROOT).trim(), this);
    }

    public void reload() {
        platform.config().reload();
        this.formatter = new Formatter(platform);
    }

    public Platform platform() {
        return platform;
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

    public PluginMeta meta() {
        return meta;
    }

    public ScheduledExecutorService executorService() {
        return executorService;
    }
}
