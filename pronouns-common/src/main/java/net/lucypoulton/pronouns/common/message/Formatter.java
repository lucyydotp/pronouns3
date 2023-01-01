package net.lucypoulton.pronouns.common.message;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.kyori.adventure.text.minimessage.tag.resolver.Placeholder;
import net.kyori.adventure.text.minimessage.tag.resolver.TagResolver;
import net.lucypoulton.pronouns.common.platform.Platform;

import java.util.Locale;


// TODO - pull this out to config;
public class Formatter {
    private final MiniMessage minimessage;
    private final Component prefix;

    public Formatter(final Platform platform) {
        minimessage = MiniMessage.builder()
                .tags(TagResolver.resolver(
                                Placeholder.parsed("main", "<reset>"),
                                Placeholder.parsed("accent", platform.config().accent()),
                                TagResolver.standard()
                        )
                ).build();

        prefix = minimessage.deserialize("<accent>Pronouns<gray> » ");
    }

    public Component accent(String content) {
        return minimessage.deserialize("<accent>" + content);
    }

    public Component translated(String key, String... args) {
        final var entry = ProNounsTranslations.registry().translate(key, Locale.ROOT);
        if (entry == null) return Component.translatable(key);
        return prefix.append(minimessage.deserialize(entry.format(args)));
    }
}
