package global.eska.ddk.keygen.account;

import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;

import static global.eska.ddk.keygen.utils.ByteUtils.bytesToBigDecimal;
import static global.eska.ddk.keygen.utils.HashUtils.sha256;

public class DDKAccountCreator implements AccountCreator {

    // TODO change for one method with string publicKey parameter
    @Override
    public BigDecimal getAddressByPublicKey(byte[] publicKey) {
        byte[] hash = sha256(publicKey);
        byte[] first8 = new byte[Long.BYTES];
        for (int i = 0; i < Long.BYTES; i++) {
            first8[i] = hash[Long.BYTES - i - 1];
        }
        return bytesToBigDecimal(first8);
    }

    @Override
    public BigDecimal getAddressByPublicKey(String publicKey) {
        return getAddressByPublicKey(publicKey.getBytes(StandardCharsets.UTF_8));
    }

}
