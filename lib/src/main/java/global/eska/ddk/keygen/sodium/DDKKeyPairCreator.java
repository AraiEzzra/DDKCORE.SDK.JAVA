package global.eska.ddk.keygen.sodium;

import com.goterl.lazycode.lazysodium.LazySodiumJava;

import static global.eska.ddk.keygen.utils.HashUtils.sha256;

public class DDKKeyPairCreator implements KeyPairCreator {

    private static final int CRYPTO_SIGN_PUBLIC_KEY_LENGTH = 32;
    private static final int CRYPTO_SIGN_PRIVATE_KEY_LENGTH = 64;

    private final LazySodiumJava lazySodium;

    public DDKKeyPairCreator(LazySodiumJava lazySodium) {
        this.lazySodium = lazySodium;
    }

    @Override
    public KeyPair createKeyPair(String passphrase) {
        byte[] seed = sha256(passphrase); // todo: exception suppressed, can be null
        byte[] publicKey = new byte[CRYPTO_SIGN_PUBLIC_KEY_LENGTH];
        byte[] privateKey = new byte[CRYPTO_SIGN_PRIVATE_KEY_LENGTH];
        lazySodium.cryptoSignSeedKeypair(publicKey, privateKey, seed); // todo: NullPointer!!!
        return new KeyPair(publicKey, privateKey);
    }
}
