package global.eska.ddk.keygen.sodium;

import com.goterl.lazycode.lazysodium.utils.Key;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class KeyPair {
    private byte[] publicKey;
    private final byte[] secretKey;

    /**
     * Alternatives is:
     * - return BaseEncoding.base16().lowerCase().encode(publicKey);
     * - return Key.fromBytes(publicKey).getAsHexString().toLowerCase();
     *
     * @return publicKey as hex lower case string
     */
    public String getPublicKeyHex() {
        return Key.fromBytes(publicKey).getAsHexString().toLowerCase();
    }

    /**
     * Alternatives is:
     * - return BaseEncoding.base16().lowerCase().encode(secretKey);
     * - return Key.fromBytes(secretKey).getAsHexString().toLowerCase();
     *
     * @return secretKey as hex lower case string
     */
    public String getSecretKeyHex() {
        return Key.fromBytes(secretKey).getAsHexString().toLowerCase();
    }
}
