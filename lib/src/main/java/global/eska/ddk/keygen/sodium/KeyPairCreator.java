package global.eska.ddk.keygen.sodium;

public interface KeyPairCreator {
    KeyPair createKeyPair(String passphrase);
}
