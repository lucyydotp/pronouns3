package net.lucypoulton.pronouns.common;

import cloud.commandframework.annotations.AnnotationParser;
import cloud.commandframework.meta.SimpleCommandMeta;
import net.lucypoulton.pronouns.api.ProNounsPlugin;
import net.lucypoulton.pronouns.api.PronounStore;
import net.lucypoulton.pronouns.api.impl.PronounParser;
import net.lucypoulton.pronouns.common.cmd.ClearCommand;
import net.lucypoulton.pronouns.common.cmd.GetCommand;
import net.lucypoulton.pronouns.common.cmd.SetCommand;
import net.lucypoulton.pronouns.common.platform.CommandSender;
import net.lucypoulton.pronouns.common.platform.Platform;
import net.lucypoulton.pronouns.common.store.InMemoryPronounStore;

public class ProNouns implements ProNounsPlugin {

    private final PronounStore store = new InMemoryPronounStore();
    private final PronounParser parser = new PronounParser(store.predefined());

    public ProNouns(Platform platform) {
        final var commandManager = platform.commandManager();
        final var annotationParser = new AnnotationParser<>(
                commandManager,
                CommandSender.class,
                params -> SimpleCommandMeta.empty()
        );


        commandManager.parserRegistry().registerSuggestionProvider("player",
                (name, ctx) -> platform.listPlayers());

        annotationParser.parse(new GetCommand(store, platform));
        annotationParser.parse(new SetCommand(this, platform));
        annotationParser.parse(new ClearCommand(store, platform));
    }

    @Override
    public PronounStore store() {
        return store;
    }

    @Override
    public PronounParser parser() {
        return parser;
    }
}
