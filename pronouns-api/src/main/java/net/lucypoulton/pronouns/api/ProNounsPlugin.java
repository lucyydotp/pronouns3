package net.lucypoulton.pronouns.api;

import net.lucypoulton.pronouns.api.impl.PronounParser;

public interface ProNounsPlugin {
    PronounParser parser();

    PronounStore store();
}
