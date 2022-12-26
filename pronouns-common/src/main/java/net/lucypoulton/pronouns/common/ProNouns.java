package net.lucypoulton.pronouns.common;

import cloud.commandframework.annotations.AnnotationParser;
import cloud.commandframework.meta.SimpleCommandMeta;
import net.kyori.adventure.translation.GlobalTranslator;
import net.lucypoulton.pronouns.api.ProNounsPlugin;
import net.lucypoulton.pronouns.api.PronounStore;
import net.lucypoulton.pronouns.api.impl.PronounParser;
import net.lucypoulton.pronouns.common.cmd.ClearCommand;
import net.lucypoulton.pronouns.common.cmd.GetCommand;
import net.lucypoulton.pronouns.common.cmd.SetCommand;
import net.lucypoulton.pronouns.common.platform.CommandSender;
import net.lucypoulton.pronouns.common.platform.Platform;

public class ProNouns implements ProNounsPlugin {

    private final PronounParser parser;
    private final Platform platform;

    public ProNouns(Platform platform) {
        this.platform = platform;
        this.parser = new PronounParser(() -> platform.store().predefined().get());

        GlobalTranslator.translator().addSource(ProNounsTranslations.registry());
        final var commandManager = platform.commandManager();
        final var annotationParser = new AnnotationParser<>(
                commandManager,
                CommandSender.class,
                params -> SimpleCommandMeta.empty()
        );


        commandManager.parserRegistry().registerSuggestionProvider("player",
                (name, ctx) -> platform.listPlayers());

        annotationParser.parse(new GetCommand(platform));
        annotationParser.parse(new SetCommand(this, platform));
        annotationParser.parse(new ClearCommand(platform));
    }

    @Override
    public PronounStore store() {
        return platform.store();
    }

    @Override
    public PronounParser parser() {
        return parser;
    }
}
