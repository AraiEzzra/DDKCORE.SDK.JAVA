package global.eska.ddk.keygen.utils;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.nio.ByteBuffer;

public class ByteUtils {

    public static BigDecimal bytesToBigDecimal(byte[] bytes) {
        final ByteBuffer buffer = ByteBuffer.allocate(Long.BYTES);
        buffer.put(bytes, 0, bytes.length);
        buffer.flip();
        BigInteger bigInt = new BigInteger(1, bytes);
        return new BigDecimal(bigInt);
    }
}
