package net.lucypoulton.pronouns.common;

import cloud.commandframework.annotations.AnnotationParser;
import cloud.commandframework.meta.SimpleCommandMeta;
import net.kyori.adventure.translation.GlobalTranslator;
import net.lucypoulton.pronouns.api.ProNounsPlugin;
import net.lucypoulton.pronouns.api.PronounStore;
import net.lucypoulton.pronouns.api.impl.PronounParser;
import net.lucypoulton.pronouns.common.cmd.*;
import net.lucypoulton.pronouns.common.message.Formatter;
import net.lucypoulton.pronouns.common.message.ProNounsTranslations;
import net.lucypoulton.pronouns.common.platform.CommandSender;
import net.lucypoulton.pronouns.common.platform.Platform;
import net.lucypoulton.pronouns.common.store.StoreFactory;

public class ProNouns implements ProNounsPlugin {

    private final PronounParser parser;
    private final Platform platform;
    private PronounStore store;

    private final Formatter formatter = new Formatter();

    public ProNouns(Platform platform) {
        this.platform = platform;

        this.parser = new PronounParser(() -> store.predefined().get());

        GlobalTranslator.translator().addSource(ProNounsTranslations.registry());
        final var commandManager = platform.commandManager();
        final var annotationParser = new AnnotationParser<>(
                commandManager,
                CommandSender.class,
                params -> SimpleCommandMeta.empty()
        );


        commandManager.parserRegistry().registerSuggestionProvider("player",
                (name, ctx) -> platform.listPlayers());

        annotationParser.parse(new GetCommand(this, platform));
        annotationParser.parse(new SetCommand(this, platform));
        annotationParser.parse(new ClearCommand(this, platform));
        annotationParser.parse(new VersionCommand(this, platform));
        annotationParser.parse(new DebugCommand(this, platform));
        annotationParser.parse(new HelpCommand(commandManager));
    }

    public void createStore(StoreFactory store) {
        this.store = store.create("nbt", platform);
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
}
