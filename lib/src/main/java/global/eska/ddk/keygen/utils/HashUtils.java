package global.eska.ddk.keygen.utils;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class HashUtils {

    private static final String SHA_256 = "SHA-256";
    private static final int SHA_256_BYTES = 32;

    public static byte[] sha256(byte[] key) {
        try {
            return MessageDigest.getInstance(SHA_256).digest(key);
        } catch (NoSuchAlgorithmException e) {
            return new byte[SHA_256_BYTES];
        }
    }

    public static byte[] sha256(String key) {
        return sha256(key.getBytes(StandardCharsets.UTF_8));
    }
}
