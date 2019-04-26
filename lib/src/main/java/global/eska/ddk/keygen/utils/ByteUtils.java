package global.eska.ddk.keygen.utils;

import java.nio.ByteBuffer;

public class ByteUtils {

    public static byte[] longToBytes(long x) {
        final ByteBuffer buffer = ByteBuffer.allocate(Long.BYTES);
        buffer.putLong(0, x);
        return buffer.array();
    }

    public static long bytesToLong(byte[] bytes) {
        final ByteBuffer buffer = ByteBuffer.allocate(Long.BYTES);
        buffer.put(bytes, 0, bytes.length);
        buffer.flip();
        return buffer.getLong();
    }
}
