package global.eska.ddk.keygen.account;

import java.math.BigInteger;

public interface AccountCreator {
    BigInteger getAddressByPublicKey(byte[] publicKey);

    BigInteger getAddressByPublicKey(String publicKey);
}
