package global.eska.ddk.keygen.account;

import java.nio.charset.StandardCharsets;

import static global.eska.ddk.keygen.utils.ByteUtils.bytesToLong;
import static global.eska.ddk.keygen.utils.HashUtils.sha256;

public class DDKAccountCreator implements AccountCreator {

    @Override
    public long getAddressByPublicKey(byte[] publicKey) {
        byte[] hash = sha256(publicKey);
        byte[] first8 = new byte[Long.BYTES];
        for (int i = 0; i < Long.BYTES; i++) {
            first8[i] = hash[Long.BYTES - i - 1];
        }
        return bytesToLong(first8);
    }

    @Override
    public long getAddressByPublicKey(String publicKey) {
        return getAddressByPublicKey(publicKey.getBytes(StandardCharsets.UTF_8));
    }

}
