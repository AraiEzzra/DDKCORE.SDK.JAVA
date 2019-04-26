package global.eska.ddk.keygen.test;

import com.goterl.lazycode.lazysodium.LazySodiumJava;
import com.goterl.lazycode.lazysodium.SodiumJava;
import global.eska.ddk.keygen.account.AccountCreator;
import global.eska.ddk.keygen.account.DDKAccountCreator;
import global.eska.ddk.keygen.passphrase.DDKPathPhraseGenerator;
import global.eska.ddk.keygen.passphrase.PassphraseGenerator;
import global.eska.ddk.keygen.sodium.DDKKeyPairCreator;
import global.eska.ddk.keygen.sodium.KeyPair;
import global.eska.ddk.keygen.sodium.KeyPairCreator;
import lombok.extern.java.Log;
import org.junit.Before;
import org.junit.Test;

import static global.eska.ddk.keygen.utils.Etalon.*;
import static global.eska.ddk.keygen.utils.HashUtils.sha256;
import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@Log
public class KeyPairCreatorTest {

    private PassphraseGenerator passphraseGenerator;

    private KeyPairCreator keyPairCreator;

    private AccountCreator accountCreator;

    @Before
    public void before() {
        this.passphraseGenerator = new DDKPathPhraseGenerator();
        this.keyPairCreator = new DDKKeyPairCreator(new LazySodiumJava(new SodiumJava()));
        this.accountCreator = new DDKAccountCreator();
    }

    @Test
    public void createPassphraseTest() {
        String passphrase = passphraseGenerator.createPassphrase();
        assertNotNull(passphrase);
        System.out.println("Random generated passphrase: " + passphrase);
    }

    @Test
    public void createKeyPairTest() {
        // todo: make different tests
        // todo: log test results
        byte[] passphraseHash = sha256(PASSPHRASE);
        assertNotNull(passphraseHash);
        assertArrayEquals(PASSPHRASE_HASH, passphraseHash);

        KeyPair keyPair = keyPairCreator.createKeyPair(PASSPHRASE);
        assertNotNull(keyPair);
        assertArrayEquals(PUBLIC_KEY_BYTES, keyPair.getPublicKey());
        assertArrayEquals(PRIVATE_KEY_BYTES, keyPair.getSecretKey());
        assertEquals("PUBLIC_KEY_HEX", PUBLIC_KEY_HEX, keyPair.getPublicKeyHex());

        long address = accountCreator.getAddressByPublicKey(keyPair.getPublicKey());
        assertEquals("ADDRESS", ADDRESS, address);
    }
}
