package net.lucypoulton.pronouns.common.util;

import java.nio.ByteBuffer;
import java.util.UUID;

public class UuidUtil {
    public static byte[] toBytes(UUID uuid) {
        final var buffer = ByteBuffer.allocate(16);
        buffer.putLong(uuid.getMostSignificantBits());
        buffer.putLong(uuid.getLeastSignificantBits());
        return buffer.array();
    }

    public static UUID fromBytes(byte[] bytes) {
        final var buffer = ByteBuffer.wrap(bytes);
        return new UUID(buffer.getLong(), buffer.getLong());
    }
}
