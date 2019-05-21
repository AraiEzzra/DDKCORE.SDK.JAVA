package global.eska.ddk.keygen.utils;

import java.math.BigInteger;
import java.nio.ByteBuffer;

public class ByteUtils {

    public static BigInteger bytesToBigInteger(byte[] bytes) {
        final ByteBuffer buffer = ByteBuffer.allocate(Long.BYTES);
        buffer.put(bytes, 0, bytes.length);
        buffer.flip();
        return new BigInteger(bytes);
    }
}
