package global.eska.ddk.keygen.account;

public interface AccountCreator {
    long getAddressByPublicKey(byte[] publicKey);

    long getAddressByPublicKey(String publicKey);
}
