package global.eska.ddk.keygen.account;

import java.math.BigDecimal;

public interface AccountCreator {
    BigDecimal getAddressByPublicKey(byte[] publicKey);

    BigDecimal getAddressByPublicKey(String publicKey);
}
