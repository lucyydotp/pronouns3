package net.lucypoulton.pronouns.common.cmd;

import cloud.commandframework.Command;
import cloud.commandframework.meta.CommandMeta;
import net.lucypoulton.pronouns.common.ProNouns;
import net.lucypoulton.pronouns.common.platform.CommandSender;
import net.lucypoulton.pronouns.common.platform.Platform;
import net.lucypoulton.pronouns.common.platform.ProNounsPermission;
import net.lucypoulton.pronouns.common.store.FilePronounStore;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DumpCommand implements ProNounsCommand {

    private static final DateFormat DATE_FORMAT = new SimpleDateFormat("d-MMM-yyyy-HH-mm");

    private final ProNouns plugin;
    private final Platform platform;

    public DumpCommand(ProNouns plugin, Platform platform) {
        this.plugin = plugin;
        this.platform = platform;
    }

    private synchronized String dump() throws IOException {
        final var dump = plugin.store().dump();
        final var date = DATE_FORMAT.format(new Date());
        final String fileName = "pronouns-dump-" + date + ".properties";
        final var path = platform.dataDir().resolve(fileName);
        FilePronounStore.writeToFile(dump, path, new Date().toString());
        return fileName;
    }

    @Override
    public Command.Builder<CommandSender> build(Command.Builder<CommandSender> builder) {
        return builder.literal("dump")
                .meta(CommandMeta.DESCRIPTION, CommandUtils.description("dump"))
                .permission(ProNounsPermission.DUMP.key)
                .handler(ctx -> {
                    final var f = plugin.formatter();
                    ctx.getSender().sendMessage(f.translated("pronouns.command.dump.start"));
                    final String fileName;
                    try {
                        fileName = dump();
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                    ctx.getSender().sendMessage(f.translated("pronouns.command.dump.finish", fileName));
                });
    }
}
