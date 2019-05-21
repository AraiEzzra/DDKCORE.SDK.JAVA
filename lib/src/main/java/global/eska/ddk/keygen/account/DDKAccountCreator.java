package global.eska.ddk.keygen.account;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;

import static global.eska.ddk.keygen.utils.ByteUtils.bytesToBigInteger;
import static global.eska.ddk.keygen.utils.HashUtils.sha256;

public class DDKAccountCreator implements AccountCreator {

    // TODO change for one method with string publicKey parameter
    @Override
    public BigInteger getAddressByPublicKey(byte[] publicKey) {
        byte[] hash = sha256(publicKey);
        byte[] first8 = new byte[Long.BYTES];
        for (int i = 0; i < Long.BYTES; i++) {
            first8[i] = hash[Long.BYTES - i - 1];
        }
        return bytesToBigInteger(first8);
    }

    @Override
    public BigInteger getAddressByPublicKey(String publicKey) {
        return getAddressByPublicKey(publicKey.getBytes(StandardCharsets.UTF_8));
    }

}
