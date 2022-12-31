package net.lucypoulton.pronouns.common.util;

import java.util.Optional;

public class EnumUtil {

    /**
     * Case-insensitively finds an enum constant by name.
     *
     * @param enumClass The enum class to look for values
     * @param name      The constant to find in the enum
     * @param <T>       The enum type
     * @return The constant if found, or an empty optional if not
     */
    public static <T extends Enum<T>> Optional<T> getByName(Class<T> enumClass, String name) {
        for (final var constant : enumClass.getEnumConstants()) {
            if (constant.name().equalsIgnoreCase(name)) return Optional.of(constant);
        }
        return Optional.empty();
    }

    private EnumUtil() {
    }
}
