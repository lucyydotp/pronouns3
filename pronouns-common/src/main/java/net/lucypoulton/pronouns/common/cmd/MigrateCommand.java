package net.lucypoulton.pronouns.common.cmd;

import cloud.commandframework.Command;
import cloud.commandframework.arguments.flags.CommandFlag;
import cloud.commandframework.arguments.standard.EnumArgument;
import cloud.commandframework.context.CommandContext;
import cloud.commandframework.meta.CommandMeta;
import net.lucypoulton.pronouns.api.set.PronounSet;
import net.lucypoulton.pronouns.common.LegacyMigrator;
import net.lucypoulton.pronouns.common.ProNouns;
import net.lucypoulton.pronouns.common.message.ProNounsTranslations;
import net.lucypoulton.pronouns.common.platform.CommandSender;

import java.nio.file.Files;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class MigrateCommand implements ProNounsCommand {

    public MigrateCommand(ProNouns plugin) {
        this.plugin = plugin;
    }

    public enum MigrationSource {
        YML,
        MYSQL
    }

    private final ProNouns plugin;

    private void execute(final CommandContext<CommandSender> context) {
        final var logger = plugin.platform().logger();

        if (!plugin.platform().migratable()) {
            logger.warning(ProNounsTranslations.translate("pronouns.migrate.invalid-platform"));
            return;
        }
        final var source = context.<MigrationSource>get("source");
        logger.info(ProNounsTranslations.translate("pronouns.migrate.start"));
        Map<UUID, List<PronounSet>> sets;

        if (source == MigrationSource.YML) {
            final var path = plugin.platform().dataDir().resolve("datastore.yml");
            if (!Files.exists(path)) {
                logger.warning("No legacy datastore found to migrate.");
                return;
            }
            sets = LegacyMigrator.fromYaml(path);
        } else {
            throw new IllegalStateException("MySQL migration not yet supported");
        }
        plugin.store().setAll(sets);
        logger.info(ProNounsTranslations.translate("pronouns.migrate.finish", sets.size()));
    }

    @Override
    public Command.Builder<CommandSender> build(final Command.Builder<CommandSender> builder) {
        return builder.literal("migrate")
            .meta(CommandMeta.DESCRIPTION, CommandUtils.description("migrate"))
            .permission(s -> s.uuid().isEmpty())
            .argument(EnumArgument.builder(MigrationSource.class, "source"))
            .flag(CommandFlag.builder("confirm"))
            .handler(this::execute);
    }
}