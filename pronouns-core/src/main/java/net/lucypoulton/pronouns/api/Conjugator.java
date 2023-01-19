package net.lucypoulton.pronouns.api;

import net.lucypoulton.pronouns.api.set.PronounSet;

/**
 * Conjugates verbs in the present simple tense - that is, takes them out of the infinitive.
 * The infinitive is the "dictionary form" of a verb, and usually starts with "to", for example "to be", "to have", etc.
 * <p>
 * An example of conjugation for "to have" might be:
 * <ul>
 * <li>conjugated for he/him: "he has"</li>
 * <li>conjugated for they/them: "they have"</li>
 * </ul>
 *
 * The present simple is used here as it's the most flexible. Most other tenses are formed by combining
 * the present simple with another verb, which is not conjugated differently depending on the subject.
 * <p>
 * Enum entries are written in the infinitive without the "to" prefix, for example BE, HAVE.
 * It goes without saying that I don't plan on listing every single verb in the English language here,
 * so only a few are provided. A simple ternary on {@link PronounSet#plural()} will work for those that aren't.
 */
public enum Conjugator {
    BE("is", "are"),
    BE_CONTRACTED("'s", "'re"),
    HAVE("has", "have"),
    DO("does", "do"),
    GO("goes", "go");

    private final String singular;
    private final String plural;

    Conjugator(final String singular, final String plural) {
        this.singular = singular;
        this.plural = plural;
    }

    public String conjugate(boolean isPlural) {
        return isPlural ? plural : singular;
    }
}
