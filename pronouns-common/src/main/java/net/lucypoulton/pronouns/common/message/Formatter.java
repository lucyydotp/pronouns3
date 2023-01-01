package net.lucypoulton.pronouns.common.message;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;

import java.util.Arrays;
import java.util.List;

// TODO - pull this out to config;
public class Formatter {

    private static final TextColor ACCENT = TextColor.color(0xffaaff);
    private static final TextColor GREY = TextColor.color(0x777777);

    private static final Component PREFIX = Component.empty()
            .append(Component.text("Pronouns").color(ACCENT))
            .append(Component.text(" » ").color(GREY));

    public Component accent(String content) {
        return Component.text(content).color(ACCENT);
    }

    public List<Component> accent(String... content) {
        return Arrays.stream(content)
                .map(this::accent)
                .toList();
    }

    public Component translated(String key, String... args) {
        return PREFIX.append(Component.translatable(key, accent(args)));
    }
}
