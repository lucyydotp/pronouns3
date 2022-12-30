package net.lucypoulton.pronouns.common.platform;

/**
 * A permission.
 */
public enum ProNounsPermission {

    SET("pronouns.set", "Access to /pn set and /pn clear", 0),
    SET_OTHER("pronouns.set.other",
            "Access to /pn set and /pn clear with the --player flag to set others' pronouns.",
            3),

    GET("pronouns.get", "Access to /pn get", 0),

    UPDATE("pronouns.update",
            "Access to /pn update",
            4),
    DEBUG("pronouns.debug",
            "Access to /pn debug",
            4);

    /**
     * This permission's key.
     */
    public final String key;

    /**
     * A description of this permission.
     */
    public final String description;

    /**
     * This permission's default level. These correspond to native Minecraft permission levels.
     *  @see <a href="https://minecraft.fandom.com/wiki/Permission_level#Java_Edition">Permission levels on MC Fandom</a>
     */
    public final int defaultLevel;
    ProNounsPermission(final String key, final String description, final int value) {
        this.key = key;
        this.description = description;
        this.defaultLevel = value;
    }
}
