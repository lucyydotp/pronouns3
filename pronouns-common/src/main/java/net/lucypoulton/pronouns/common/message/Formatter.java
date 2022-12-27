package net.lucypoulton.pronouns.common.message;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;

import java.util.Arrays;
import java.util.List;

public class Formatter {
    public Component accent(String content) {
        return Component.text(content).color(TextColor.color(0xffaaff));
    }

    public List<Component> accent(String... content) {
        return Arrays.stream(content)
                .map(this::accent)
                .toList();
    }

    public Component translated(String key, String... args) {
        return Component.translatable(key, accent(args));
    }
}
