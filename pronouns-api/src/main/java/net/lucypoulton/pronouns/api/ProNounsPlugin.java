package net.lucypoulton.pronouns.api;

import net.lucypoulton.pronouns.api.impl.PronounParser;

/**
 * The entry point for the plugin.
 */
public interface ProNounsPlugin {
    /**
     * Gets a pronoun parser that is aware of the predefined sets from {@link #store()}.
     */
    PronounParser parser();

    /**
     * Gets the pronoun store.
     */
    PronounStore store();
}
